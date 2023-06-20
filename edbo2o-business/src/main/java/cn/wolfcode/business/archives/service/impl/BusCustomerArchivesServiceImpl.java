package cn.wolfcode.business.archives.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.wolfcode.business.archives.domain.BusCustomerArchives;
import cn.wolfcode.business.archives.domain.vo.BusCustomerArchivesVO;

import cn.wolfcode.business.archives.mapper.BusCustomerArchivesMapper;
import cn.wolfcode.business.archives.service.IBusCustomerArchivesService;
import cn.wolfcode.common.core.domain.entity.SysUser;
import cn.wolfcode.common.utils.SecurityUtils;
import cn.wolfcode.system.mapper.SysUserMapper;
import cn.wolfcode.utils.MapStructs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * 客户档案Service业务层处理
 *
 * @author wolfcode
 * @date 2023-06-09
 */
@Service
public class BusCustomerArchivesServiceImpl implements IBusCustomerArchivesService {
    @Autowired
    private BusCustomerArchivesMapper busCustomerFollowUpMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    /**
     * 查询客户档案
     *
     * @param id 客户档案主键
     * @return 客户档案
     */
    @Override
    public BusCustomerArchives selectBusCustomerFollowUpById(Long id) {
        return busCustomerFollowUpMapper.selectBusCustomerFollowUpById(id);
    }

    /**
     * 查询客户档案列表
     *
     * @param busCustomerFollowUp 客户档案
     * @return 客户档案
     */
    @Override
    public List<BusCustomerArchives> selectBusCustomerFollowUpList(BusCustomerArchives busCustomerFollowUp) {
        return busCustomerFollowUpMapper.selectBusCustomerFollowUpList(busCustomerFollowUp);
    }

    public List<BusCustomerArchives> selectBusCustomerFollowUpListExport(BusCustomerArchives busCustomerFollowUp) {
        return busCustomerFollowUpMapper.selectBusCustomerFollowUpListExport(busCustomerFollowUp);
    }

    /**
     * 新增客户档案
     *
     * @param busCustomerFollowUpVO 客户档案
     * @return 结果
     */
    @Override
    public int insertBusCustomerFollowUp(BusCustomerArchivesVO busCustomerFollowUpVO) {
        Assert.notNull(busCustomerFollowUpVO, "非法操作");
        Assert.isNull(busCustomerFollowUpMapper.selectBusCustomerFollowUpByPhone(busCustomerFollowUpVO.getCustomerPhone()), "电话已存在");
        BusCustomerArchives busCustomerFollowUp = MapStructs.INSTANCE.dto2po(busCustomerFollowUpVO);
        MapStructs.INSTANCE.map2po(busCustomerFollowUp, new HashMap<String, Object>() {{
            this.put("customerVip", BusCustomerArchivesVO.VIP_NO);
            this.put("enterUser", SecurityUtils.getUsername());
            this.put("isPayRecord", BusCustomerArchivesVO.PAY_RECORD_NO);
        }});
        return busCustomerFollowUpMapper.insertBusCustomerFollowUp(busCustomerFollowUp);
    }

    /**
     * 修改客户档案
     *
     * @param busCustomerFollowUpVO 客户档案
     * @return 结果
     */
    @Override
    public int updateBusCustomerFollowUp(BusCustomerArchivesVO busCustomerFollowUpVO) {
        Assert.notNull(busCustomerFollowUpVO, "非法操作");
        BusCustomerArchives busCustomerFollowUp = MapStructs.INSTANCE.dto2po(busCustomerFollowUpVO);
        return busCustomerFollowUpMapper.updateBusCustomerFollowUp(busCustomerFollowUp);
    }

    /**
     * 批量删除客户档案
     *
     * @param ids 需要删除的客户档案主键
     * @return 结果
     */
    @Override
    public int deleteBusCustomerFollowUpByIds(Long[] ids) {
        return busCustomerFollowUpMapper.deleteBusCustomerFollowUpByIds(ids);
    }

    /**
     * 删除客户档案信息
     *
     * @param id 客户档案主键
     * @return 结果
     */
    @Override
    public int deleteBusCustomerFollowUpById(Long id) {
        return busCustomerFollowUpMapper.deleteBusCustomerFollowUpById(id);
    }

    @Override
    public void CopyCustomerFollowUpInput(Map<String, Object> map) {
        if (busCustomerFollowUpMapper.selectBusCustomerFollowUpByPhone(map.get("phone").toString()) == null) {
            BusCustomerArchives busCustomerFollowUp = setNameAndPhoneAndCarSeries(map);
            busCustomerFollowUp.setEnterUser(SecurityUtils.getUsername());
            busCustomerFollowUp.setIsPayRecord(Integer.valueOf(map.get("payRecord").toString()));
            checkSysUser(map, busCustomerFollowUp);
            busCustomerFollowUpMapper.insertBusCustomerFollowUp(busCustomerFollowUp);
        }
    }

    @Override
    public void CopyCustomerFollowUpUpdate(Map<String, Object> map) {
        Long id = busCustomerFollowUpMapper.selectBusCustomerFollowUpByPhone(map.get("phone").toString());
        BusCustomerArchives busCustomerFollowUp = setNameAndPhoneAndCarSeries(map);
        if (!ObjectUtils.isEmpty(id)) {
            busCustomerFollowUp.setId(id);
            busCustomerFollowUpMapper.updateBusCustomerFollowUp(busCustomerFollowUp);
        } else {
            busCustomerFollowUp.setIsPayRecord(Integer.valueOf(map.get("payRecord").toString()));
            busCustomerFollowUp.setEnterUser(SecurityUtils.getUsername());
            checkSysUser(map, busCustomerFollowUp);
            busCustomerFollowUpMapper.insertBusCustomerFollowUp(busCustomerFollowUp);
        }
    }

    @Override
    public List<String> enter() {
        return busCustomerFollowUpMapper.selectBusCustomerFollowUpEnterList();
    }

    private static BusCustomerArchives setNameAndPhoneAndCarSeries(Map<String, Object> map) {
        BusCustomerArchives busCustomerFollowUp = new BusCustomerArchives();
        MapStructs.INSTANCE.map2po(busCustomerFollowUp, map);
        return busCustomerFollowUp;
    }

    private void checkSysUser(Map<String, Object> map, BusCustomerArchives busCustomerFollowUp) {
        SysUser sysUser = sysUserMapper.selectUserByPhone(map.get("phone").toString());
        if (ObjectUtils.isEmpty(sysUser)) {
            busCustomerFollowUp.setGender(2);
            busCustomerFollowUp.setCustomerVip(BusCustomerArchivesVO.VIP_NO);
        } else {
            busCustomerFollowUp.setGender(Integer.parseInt(sysUser.getSex()));
            busCustomerFollowUp.setCustomerVip(BusCustomerArchivesVO.VIP_YES);
        }
    }
}
