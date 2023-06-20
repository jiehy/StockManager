package cn.wolfcode.business.appointment.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wolfcode.business.appointment.domain.BusServiceItem;
import cn.wolfcode.business.appointment.domain.vo.BusAuditDTO;
import cn.wolfcode.business.appointment.domain.vo.BusAuditVO;
import cn.wolfcode.business.appointment.domain.vo.BusServiceItemVO;
import cn.wolfcode.business.appointment.mapper.BusServiceItemMapper;
import cn.wolfcode.business.appointment.service.IBusServiceItemService;
import cn.wolfcode.business.flowdefine.domain.BusBpmnInfo;
import cn.wolfcode.business.flowdefine.domain.BusCarPackageAudit;
import cn.wolfcode.business.flowdefine.domain.vo.BusBpmnInfoVO;
import cn.wolfcode.business.flowdefine.mapper.BusBpmnInfoMapper;
import cn.wolfcode.business.flowdefine.mapper.BusCarPackageAuditMapper;
import cn.wolfcode.common.core.domain.entity.SysUser;
import cn.wolfcode.common.utils.SecurityUtils;
import cn.wolfcode.common.utils.StringUtils;
import cn.wolfcode.system.mapper.SysUserMapper;
import cn.wolfcode.system.service.ISysConfigService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;


/**
 * 服务项Service业务层处理
 *
 * @author ruoyi
 * @date 2023-05-30
 */
@Service
public class BusServiceItemServiceImpl implements IBusServiceItemService {

    private static final String regExp = new ToStringBuilder("(")
            .append(BusServiceItemVO.AUDIT_STATUS_INITIALIZE).append("|")
            .append(BusServiceItemVO.AUDIT_STATUS_REVIEW).append("|")
            .append(BusServiceItemVO.AUDIT_STATUS_REFUSED).append(")")
            .toString();

    private static final String ILLEGAL_OPERATION = "非法操作";

    private static final String PRICE_WRONG = "折扣价不能大于原价";

    @Autowired
    private BusServiceItemMapper busServiceItemMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private BusBpmnInfoMapper busBpmnInfoMapper;

    @Autowired
    private BusCarPackageAuditMapper carPackageAuditMapper;

    /**
     * 查询服务项
     *
     * @param id 服务项主键
     * @return 服务项
     */
    @Override
    public BusServiceItem selectBusServiceItemById(Long id) {
        return busServiceItemMapper.selectBusServiceItemById(id);
    }

    /**
     * 查询服务项列表
     *
     * @param busServiceItem 服务项
     * @return 服务项
     */
    @Override
    public List<BusServiceItem> selectBusServiceItemList(BusServiceItem busServiceItem) {
        return busServiceItemMapper.selectBusServiceItemList(busServiceItem);
    }

    /**
     * 新增服务项
     *
     * @param busServiceItemVO 服务项
     * @return 结果
     */
    @Override
    public int insertBusServiceItem(BusServiceItemVO busServiceItemVO) {
        checkVo(busServiceItemVO);
        BusServiceItem busServiceItem = new BusServiceItem();
        copyBusServiceItem(busServiceItemVO, busServiceItem);
        return busServiceItemMapper.insertBusServiceItem(busServiceItem);
    }

    /**
     * 修改服务项
     *
     * @param busServiceItemVO 服务项
     * @return 结果
     */
    @Override
    public int updateBusServiceItem(BusServiceItemVO busServiceItemVO) {
        checkVo(busServiceItemVO);
        BusServiceItem busServiceItem = checkBusServiceItem(busServiceItemVO.getId());
        flag(!(BusServiceItemVO.AUDIT_STATUS_REVIEW.equals(busServiceItem.getAuditStatus())
                || BusServiceItemVO.SALE_STATUS_ON.equals(busServiceItem.getSaleStatus())), "不符合修改条件");
        copyBusServiceItem(busServiceItemVO, busServiceItem);
        return busServiceItemMapper.updateBusServiceItem(busServiceItem);
    }

    /**
     * 批量删除服务项
     *
     * @param ids 需要删除的服务项主键
     * @return 结果
     */
    @Override
    public int deleteBusServiceItemByIds(Long[] ids) {
        return busServiceItemMapper.deleteBusServiceItemByIds(ids);
    }

    /**
     * 删除服务项信息
     *
     * @param id 服务项主键
     * @return 结果
     */
    @Override
    public int deleteBusServiceItemById(Long id) {
        return busServiceItemMapper.deleteBusServiceItemById(id);
    }

    @Override
    public int saleOn(Long id) {
        BusServiceItem busServiceItem = checkBusServiceItem(id);
        flag(!(String.valueOf(busServiceItem.getAuditStatus()).matches(regExp)
                || BusServiceItemVO.SALE_STATUS_ON.equals(busServiceItem.getSaleStatus())), "不符合上架条件");
        return busServiceItemMapper.changeSaleStatus(id, BusServiceItemVO.SALE_STATUS_ON);
    }

    @Override
    public int saleOff(Long id) {
        BusServiceItem busServiceItem = checkBusServiceItem(id);
        flag(!BusServiceItemVO.SALE_STATUS_OFF.equals(busServiceItem.getSaleStatus()), "不符合下架条件");
        return busServiceItemMapper.changeSaleStatus(id, BusServiceItemVO.SALE_STATUS_OFF);
    }

    @Override
    public BusAuditVO audit(Long id) {
        BusServiceItem busServiceItem = checkBusServiceItem(id);
        Assert.state(BusServiceItemVO.CAR_PACKAGE_YES.equals(busServiceItem.getCarPackage()), "必须是套餐才能审核");
        Assert.state(BusServiceItemVO.SALE_STATUS_OFF.equals(busServiceItem.getSaleStatus()), "必须是下架状态才能审核");
        Assert.state(BusServiceItemVO.AUDIT_STATUS_INITIALIZE.equals(busServiceItem.getAuditStatus())
                        || BusServiceItemVO.AUDIT_STATUS_REFUSED.equals(busServiceItem.getAuditStatus())
                , "必须在初始化/重新编辑才能审核");

        List<SysUser> shopOwner = sysUserMapper.selectUserByRoleName("店长");
        String limit = configService.selectConfigByKey("limit");

        List<SysUser> finances = null;
        if(busServiceItem.getDiscountPrice().compareTo(new BigDecimal(limit)) > -1) {
            finances = sysUserMapper.selectUserByRoleName("财务");
        }

        BusAuditVO busAuditVO = new BusAuditVO();
        busAuditVO.setServiceItem(busServiceItem);
        busAuditVO.setShopOwner(shopOwner);
        busAuditVO.setFinances(finances);
        busAuditVO.setLimit(new BigDecimal(limit));

        return busAuditVO;
    }

    @Override
    @Transactional
    public int subAudit(BusAuditDTO dto) {
        Assert.notNull(dto.getShopOwnerId(), "非法操作");
        BusServiceItem busServiceItem = checkBusServiceItem(dto.getId());

        String key = busBpmnInfoMapper.selectBusBpmnInfoBytype(BusBpmnInfoVO.PACKAGE_TYPE);
        Assert.hasLength(key,"请先定义流程");

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(key, new HashMap<String, Object>() {{
            put("shopOwnerId", dto.getShopOwnerId());
            put("financeId", dto.getFinanceId());
            put("disCountPrice", busServiceItem.getDiscountPrice().longValue());
        }});

        BusCarPackageAudit carPackageAudit = new BusCarPackageAudit();
        carPackageAudit.setServiceItemId(busServiceItem.getId());
        carPackageAudit.setServiceItemName(busServiceItem.getName());
        carPackageAudit.setServiceItemPrice(busServiceItem.getDiscountPrice());
        carPackageAudit.setServiceItemInfo(busServiceItem.getInfo());
        carPackageAudit.setInstanceId(processInstance.getProcessInstanceId());
        carPackageAudit.setCreatorId(SecurityUtils.getUserId().toString());
        carPackageAudit.setInfo(dto.getInfo());
        carPackageAudit.setStatus(BusCarPackageAudit.STATUS_IN_ROGRESS);
        carPackageAuditMapper.insertBusCarPackageAudit(carPackageAudit);

        runtimeService.updateBusinessKey(processInstance.getProcessInstanceId(),carPackageAudit.getId().toString());
        return busServiceItemMapper.changeAuditStatusById(busServiceItem.getId(),BusServiceItemVO.AUDIT_STATUS_REVIEW);
    }

    private static void objectIsEmpty(Object object, String msg) {
        if (ObjectUtils.isEmpty(object)) {
            throw new RuntimeException(msg);
        }
    }

    private static void strIsEmpty(String str, String msg) {
        if (StringUtils.isEmpty(str)) {
            throw new RuntimeException(msg);
        }
    }

    private static void flag(boolean flag, String msg) {
        if (!flag) {
            throw new RuntimeException(msg);
        }
    }


    private static void checkVo(BusServiceItemVO busServiceItemVO) {
        objectIsEmpty(busServiceItemVO, ILLEGAL_OPERATION);
        strIsEmpty(busServiceItemVO.getName(), ILLEGAL_OPERATION);
        objectIsEmpty(busServiceItemVO.getOriginalPrice(), ILLEGAL_OPERATION);
        objectIsEmpty(busServiceItemVO.getDiscountPrice(), ILLEGAL_OPERATION);
        objectIsEmpty(busServiceItemVO.getCarPackage(), ILLEGAL_OPERATION);
        objectIsEmpty(busServiceItemVO.getServiceCatalog(), ILLEGAL_OPERATION);
        flag(String.valueOf(busServiceItemVO.getCarPackage()).matches("(0|1)"), ILLEGAL_OPERATION);
        flag(busServiceItemVO.getDiscountPrice().compareTo(busServiceItemVO.getOriginalPrice()) <= 0, PRICE_WRONG);
    }

    private static void copyBusServiceItem(BusServiceItemVO busServiceItemVO, BusServiceItem busServiceItem) {
        BeanUtils.copyProperties(busServiceItemVO, busServiceItem);
        if (BusServiceItemVO.CAR_PACKAGE_YES.equals(busServiceItemVO.getCarPackage())) {
            busServiceItem.setAuditStatus(BusServiceItemVO.AUDIT_STATUS_INITIALIZE);
        }
        if (BusServiceItemVO.CAR_PACKAGE_NO.equals(busServiceItemVO.getCarPackage())) {
            busServiceItem.setAuditStatus(BusServiceItemVO.AUDIT_STATUS_NOT_REVIEW);
        }
    }

    private BusServiceItem checkBusServiceItem(Long id) {
        objectIsEmpty(id, ILLEGAL_OPERATION);
        BusServiceItem busServiceItem = busServiceItemMapper.selectBusServiceItemById(id);
        objectIsEmpty(busServiceItem, "没有此服务项");
        return busServiceItem;
    }
}
