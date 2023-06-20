package cn.wolfcode.customer.visit.mapper;

import cn.wolfcode.customer.visit.domain.CustomerVisit;

import java.util.List;

/**
 * 客户拜访Mapper接口
 *
 * @author HH
 * @date 2023-06-16
 */
public interface CustomerVisitMapper {
    /**
     * 查询客户拜访
     *
     * @param id 客户拜访主键
     * @return 客户拜访
     */
    public CustomerVisit selectCustomerVisitById(Long id);

    /**
     * 查询客户拜访列表
     *
     * @param customerVisit 客户拜访
     * @return 客户拜访集合
     */
    public List<CustomerVisit> selectCustomerVisitList(CustomerVisit customerVisit);

    /**
     * 新增客户拜访
     *
     * @param customerVisit 客户拜访
     * @return 结果
     */
    public int insertCustomerVisit(CustomerVisit customerVisit);

    /**
     * 修改客户拜访
     *
     * @param customerVisit 客户拜访
     * @return 结果
     */
    public int updateCustomerVisit(CustomerVisit customerVisit);

    /**
     * 删除客户拜访
     *
     * @param id 客户拜访主键
     * @return 结果
     */
    public int deleteCustomerVisitById(Long id);

    /**
     * 批量删除客户拜访
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteCustomerVisitByIds(Long[] ids);
}
