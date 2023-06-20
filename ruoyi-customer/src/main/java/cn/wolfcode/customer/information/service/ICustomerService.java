package cn.wolfcode.customer.information.service;

import cn.wolfcode.customer.information.domain.vo.CustomerVO;
import cn.wolfcode.customer.information.domain.Customer;

import java.util.List;

/**
 * 客户基本信息管理Service接口
 *
 * @author HH
 * @date 2023-06-16
 */
public interface ICustomerService {
    /**
     * 查询客户基本信息管理
     *
     * @param id 客户基本信息管理主键
     * @return 客户基本信息管理
     */
    public Customer selectCustomerById(Long id);

    /**
     * 查询客户基本信息管理列表
     *
     * @param customer 客户基本信息管理
     * @return 客户基本信息管理集合
     */
    public List<Customer> selectCustomerList(Customer customer);

    /**
     * 新增客户基本信息管理
     *
     * @param customer 客户基本信息管理
     * @return 结果
     */
    public int insertCustomer(CustomerVO customer);

    /**
     * 修改客户基本信息管理
     *
     * @param customer 客户基本信息管理
     * @return 结果
     */
    public int updateCustomer(CustomerVO customer);

    /**
     * 批量删除客户基本信息管理
     *
     * @param ids 需要删除的客户基本信息管理主键集合
     * @return 结果
     */
    public int deleteCustomerByIds(Long[] ids);

    /**
     * 删除客户基本信息管理信息
     *
     * @param id 客户基本信息管理主键
     * @return 结果
     */
    public int deleteCustomerById(Long id);

    List<String> selectCustomerListByname();

}
