package cn.wolfcode.customer.information.mapper;

import cn.wolfcode.customer.information.domain.Customer;

import java.util.List;

/**
 * 客户基本信息管理Mapper接口
 *
 * @author HH
 * @date 2023-06-16
 */
public interface CustomerMapper {
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
    public int insertCustomer(Customer customer);

    /**
     * 修改客户基本信息管理
     *
     * @param customer 客户基本信息管理
     * @return 结果
     */
    public int updateCustomer(Customer customer);

    /**
     * 删除客户基本信息管理
     *
     * @param id 客户基本信息管理主键
     * @return 结果
     */
    public int deleteCustomerById(Long id);

    /**
     * 批量删除客户基本信息管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCustomerByIds(Long[] ids);

    List<String> selectCustomerListByname();

}
