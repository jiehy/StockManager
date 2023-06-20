package cn.wolfcode.customer.information.service.impl;

import cn.wolfcode.customer.information.domain.vo.CustomerVO;
import cn.wolfcode.common.utils.SecurityUtils;
import cn.wolfcode.customer.information.domain.Customer;
import cn.wolfcode.customer.information.mapper.CustomerMapper;
import cn.wolfcode.customer.information.service.ICustomerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * 客户基本信息管理Service业务层处理
 *
 * @author HH
 * @date 2023-06-16
 */
@Service
public class CustomerServiceImpl implements ICustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 查询客户基本信息管理
     *
     * @param id 客户基本信息管理主键
     * @return 客户基本信息管理
     */
    @Override
    public Customer selectCustomerById(Long id) {
        return customerMapper.selectCustomerById(id);
    }

    /**
     * 查询客户基本信息管理列表
     *
     * @param customer 客户基本信息管理
     * @return 客户基本信息管理
     */
    @Override
    public List<Customer> selectCustomerList(Customer customer) {
        return customerMapper.selectCustomerList(customer);
    }

    /**
     * 新增客户基本信息管理
     *
     * @param customerVO 客户基本信息管理
     * @return 结果
     */
    @Override
    public int insertCustomer(CustomerVO customerVO) {
        //1 参数校验
        check(customerVO);
        //2 将vo赋值给对象
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerVO, customer);
        //3 设置录入人
        customer.setEntryPerson(SecurityUtils.getUsername());
        //4 设置营销人
        customer.setSaleMan(SecurityUtils.getUsername());
        //5 设置录入时间
        customer.setEntryTime(new Date());
        return customerMapper.insertCustomer(customer);
    }

    private void check(CustomerVO customerVO) {
        Assert.notNull(customerVO, "非法操作");
        Assert.notNull(customerVO.getName(), "企业名称不能为空");
        Assert.notNull(customerVO.getLegalRepresentative(), "法定代表人不能为空");
        Assert.notNull(customerVO.getDateOfEstablishment(), "成立日期不能为空");
        Assert.notNull(customerVO.getOperatingStatus(), "经营状态不能为空");
        Assert.notNull(customerVO.getRegisteredCapital(), "注册资金不能为空");
        Assert.notNull(customerVO.getIndustry(), "所属行业不能为空");
        Assert.notNull(customerVO.getProvince(), "所属省份不能为空");
        Assert.notNull(customerVO.getAddress(), "注册地址不能为空");
        Assert.notNull(customerVO.getBusinessScope(), "经营范围不能为空");
        if (customerVO.getDateOfEstablishment().after(new Date())) {
            throw new RuntimeException("成立日期不符合条件");
        }
        if (customerVO.getName().length() > 100) {
            throw new RuntimeException("字数限制100以内");
        }
        if (customerVO.getLegalRepresentative().length() > 30) {
            throw new RuntimeException("字数限制30以内");
        }
        if (customerVO.getIndustry().length() > 30) {
            throw new RuntimeException("字数限制30以内");
        }
        if (customerVO.getAddress().length() > 500) {
            throw new RuntimeException("字数限制500以内");
        }
        if (customerVO.getAddress().length() > 500) {
            throw new RuntimeException("字数限制500以内");
        }
    }

    /**
     * 修改客户基本信息管理
     *
     * @param customerVO 客户基本信息管理
     * @return 结果
     */
    @Override
    public int updateCustomer(CustomerVO customerVO) {
        //1 参数校验
        check(customerVO);
        //2 通过id 去数据库查询数据
        Customer customer = customerMapper.selectCustomerById(customerVO.getId());
        if (customer == null) {
            throw new RuntimeException("非法操作");
        }
        //3 把vo赋值给对象
        BeanUtils.copyProperties(customerVO, customer);
        return customerMapper.updateCustomer(customer);
    }

    /**
     * 批量删除客户基本信息管理
     *
     * @param ids 需要删除的客户基本信息管理主键
     * @return 结果
     */
    @Override
    public int deleteCustomerByIds(Long[] ids) {
        return customerMapper.deleteCustomerByIds(ids);
    }

    /**
     * 删除客户基本信息管理信息
     *
     * @param id 客户基本信息管理主键
     * @return 结果
     */
    @Override
    public int deleteCustomerById(Long id) {
        return customerMapper.deleteCustomerById(id);
    }

    @Override
    public List<String> selectCustomerListByname() {
        return customerMapper.selectCustomerListByname();
    }


}
