package cn.wolfcode.business.appointment.service.impl;

import java.util.HashMap;
import java.util.List;

import cn.wolfcode.business.appointment.domain.vo.BusStatementVO;
import cn.wolfcode.business.appointment.util.RegexUtils;
import cn.wolfcode.business.appointment.util.VehiclePlateNoUtil;

import cn.wolfcode.business.archives.domain.vo.BusCustomerArchivesVO;
import cn.wolfcode.business.archives.service.IBusCustomerArchivesService;
import cn.wolfcode.common.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.business.appointment.mapper.BusStatementMapper;
import cn.wolfcode.business.appointment.domain.BusStatement;
import cn.wolfcode.business.appointment.service.IBusStatementService;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * 结算单Service业务层处理
 * 
 * @author wolfcode
 * @date 2023-05-31
 */
@Service
public class BusStatementServiceImpl implements IBusStatementService 
{
    private static final String ILLEGAL_OPERATION = "非法操作";
    @Autowired
    private BusStatementMapper busStatementMapper;

    @Autowired
    private IBusCustomerArchivesService busCustomerFollowUpService;

    /**
     * 查询结算单
     * 
     * @param id 结算单主键
     * @return 结算单
     */
    @Override
    public BusStatement selectBusStatementById(Long id)
    {
        return busStatementMapper.selectBusStatementById(id);
    }

    /**
     * 查询结算单列表
     * 
     * @param busStatement 结算单
     * @return 结算单
     */
    @Override
    public List<BusStatement> selectBusStatementList(BusStatement busStatement)
    {
        return busStatementMapper.selectBusStatementList(busStatement);
    }

    /**
     * 新增结算单
     * 
     * @param busStatementVO 结算单
     * @return 结果
     */
    @Override
    public int insertBusStatement(BusStatementVO busStatementVO)
    {
//        checkVo(busStatementVO);
        BusStatement busStatement = new BusStatement();
        BeanUtils.copyProperties(busStatementVO,busStatement);
        busStatement.setStatus(BusStatementVO.STATUS_NOT_PAY);

        busCustomerFollowUpService.CopyCustomerFollowUpInput(new HashMap<String, Object>() {{
            put("phone",busStatementVO.getCustomerPhone());
            put("name",busStatementVO.getCustomerName());
            put("carSeries",busStatementVO.getCarSeries());
            put("payRecord", BusCustomerArchivesVO.PAY_RECORD_NO);
        }});

        return busStatementMapper.insertBusStatement(busStatement);
    }

    /**
     * 修改结算单
     * 
     * @param busStatementVO 结算单
     * @return 结果
     */
    @Override
    public int updateBusStatement(BusStatementVO busStatementVO)
    {
        checkVo(busStatementVO);
        BusStatement busStatement = checkBusStatement(busStatementVO.getId());
        Assert.state(BusStatementVO.STATUS_NOT_PAY.equals(busStatement.getStatus()),"已支付不能修改");
        BeanUtils.copyProperties(busStatementVO,busStatement);

        busCustomerFollowUpService.CopyCustomerFollowUpUpdate(new HashMap<String, Object>() {{
            put("phone",busStatementVO.getCustomerPhone());
            put("name",busStatementVO.getCustomerName());
            put("carSeries",busStatementVO.getCarSeries());
            put("payRecord",BusCustomerArchivesVO.PAY_RECORD_NO);
        }});

        return busStatementMapper.updateBusStatement(busStatement);
    }

    /**
     * 批量删除结算单
     * 
     * @param ids 需要删除的结算单主键
     * @return 结果
     */
    @Override
    public int deleteBusStatementByIds(Long[] ids)
    {
        return busStatementMapper.deleteBusStatementByIds(ids);
    }

    /**
     * 删除结算单信息
     * 
     * @param id 结算单主键
     * @return 结果
     */
    @Override
    public int deleteBusStatementById(Long id)
    {
        isNull(id,ILLEGAL_OPERATION);
        BusStatement busStatement = checkBusStatement(id);
        Assert.state(BusStatementVO.STATUS_NOT_PAY.equals(busStatement.getStatus()),"已支付不能删除");
        return busStatementMapper.updateBusStatementStatusById(id);
    }

    @Override
    public void sync(BusStatement busStatement) {
        busCustomerFollowUpService.CopyCustomerFollowUpInput(new HashMap<String, Object>() {{
            put("phone", busStatement.getCustomerPhone());
            put("name", busStatement.getCustomerName());
            put("carSeries", busStatement.getCarSeries());
            put("payRecord", busStatement.getStatus());
        }});
    }

    private static void isNull(Object object,String msg) {
        if (ObjectUtils.isEmpty(object)) {
            throw new RuntimeException(msg);
        }
    }

    private static void isEmpty(String str,String msg) {
        if(StringUtils.isEmpty(str)) {
            throw new RuntimeException(msg);
        }
    }

    private static void checkVo(BusStatementVO busStatementVO) {
        isNull(busStatementVO,ILLEGAL_OPERATION);
        isNull(busStatementVO.getServiceType(),"服务类型不能为空");
        isEmpty(busStatementVO.getCustomerName(),"客户姓名不能为空");
        isEmpty(busStatementVO.getCustomerPhone(),"联系方式不能为空");
        isEmpty(busStatementVO.getLicensePlate(),"车牌号不能为空");
        isEmpty(busStatementVO.getCarSeries(),"汽车类型不能为空");
        Assert.state(RegexUtils.isPhoneLegal(busStatementVO.getCustomerPhone()),"联系方式错误");
        isNull(VehiclePlateNoUtil.getVehiclePlateNo(busStatementVO.getLicensePlate()),"车牌号错误");
    }

    private BusStatement checkBusStatement(Long id) {
        BusStatement busStatement = busStatementMapper.selectBusStatementById(id);
        isNull(busStatement,"没有此结算单");
        return busStatement;
    }
}
