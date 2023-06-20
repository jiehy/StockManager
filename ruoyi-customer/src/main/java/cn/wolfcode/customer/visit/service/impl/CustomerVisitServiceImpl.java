package cn.wolfcode.customer.visit.service.impl;

import cn.wolfcode.customer.visit.domain.CustomerVisit;
import cn.wolfcode.customer.visit.domain.vo.CustomerVisitVO;
import cn.wolfcode.customer.visit.mapper.CustomerVisitMapper;
import cn.wolfcode.customer.visit.service.ICustomerVisitService;
import cn.wolfcode.common.utils.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * 客户拜访Service业务层处理
 *
 * @author HH
 * @date 2023-06-16
 */
@Service
public class CustomerVisitServiceImpl implements ICustomerVisitService {
    @Autowired
    private CustomerVisitMapper customerVisitMapper;

    /**
     * 查询客户拜访
     *
     * @param id 客户拜访主键
     * @return 客户拜访
     */
    @Override
    public CustomerVisit selectCustomerVisitById(Long id) {
        return customerVisitMapper.selectCustomerVisitById(id);
    }

    /**
     * 查询客户拜访列表
     *
     * @param customerVisit 客户拜访
     * @return 客户拜访
     */
    @Override
    public List<CustomerVisit> selectCustomerVisitList(CustomerVisit customerVisit) {
        return customerVisitMapper.selectCustomerVisitList(customerVisit);
    }

    /**
     * 新增客户拜访
     *
     * @param customerVisitVO 客户拜访
     * @return 结果
     */
    @Override
    public int insertCustomerVisit(CustomerVisitVO customerVisitVO) {
        //1 参数校验
        check(customerVisitVO);
        //2 把vo的赋值给对象
        CustomerVisit customerVisit = new CustomerVisit();
        BeanUtils.copyProperties(customerVisitVO, customerVisit);
        //3 设置录入人
        customerVisit.setEntryPerson(SecurityUtils.getUsername());
        //4 设置录入时间
        customerVisit.setEntryTime(new Date());
        //5 设置拜访日期默认为当日
        customerVisit.setEntryTime(new Date());
        return customerVisitMapper.insertCustomerVisit(customerVisit);
    }

    private void check(CustomerVisitVO customerVisitVO) {
        Assert.notNull(customerVisitVO, "非法操作");
        Assert.notNull(customerVisitVO.getCustomerName(), "企业名称不能为空");
        Assert.notNull(customerVisitVO.getContactPersonName(), "联系人名称不能为空");
        Assert.notNull(customerVisitVO.getVisitReason(), "拜访原因不能为空");
        Assert.notNull(customerVisitVO.getVisitWay(), "拜访方式不能为空");
        Assert.notNull(customerVisitVO.getVisitDate(), "拜访日期不能为空");
        Assert.notNull(customerVisitVO.getCommunication(), "交流情况不能为空");

        if (customerVisitVO.getVisitReason().length() > 100) {
            throw new RuntimeException("100字以内");
        }

        if (customerVisitVO.getCommunication().length() > 1000) {
            throw new RuntimeException("1000字以内");
        }
    }

    /**
     * 修改客户拜访
     *
     * @param customerVisit 客户拜访
     * @return 结果
     */
    @Override
    public int updateCustomerVisit(CustomerVisit customerVisit) {
        return customerVisitMapper.updateCustomerVisit(customerVisit);
    }

    /**
     * 批量删除客户拜访
     *
     * @param ids 需要删除的客户拜访主键
     * @return 结果
     */
    @Override
    public int deleteCustomerVisitByIds(Long[] ids) {
        return customerVisitMapper.deleteCustomerVisitByIds(ids);
    }

    /**
     * 删除客户拜访信息
     *
     * @param id 客户拜访主键
     * @return 结果
     */
    @Override
    public int deleteCustomerVisitById(Long id) {
        return customerVisitMapper.deleteCustomerVisitById(id);
    }
}
