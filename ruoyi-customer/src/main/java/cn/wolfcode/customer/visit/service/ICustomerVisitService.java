package cn.wolfcode.customer.visit.service;

import cn.wolfcode.customer.visit.domain.CustomerVisit;
import cn.wolfcode.customer.visit.domain.vo.CustomerVisitVO;

import java.util.List;

/**
 * 客户拜访Service接口
 *
 * @author HH
 * @date 2023-06-16
 */
public interface ICustomerVisitService {
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
    public int insertCustomerVisit(CustomerVisitVO customerVisit);

    /**
     * 修改客户拜访
     *
     * @param customerVisit 客户拜访
     * @return 结果
     */
    public int updateCustomerVisit(CustomerVisit customerVisit);

    /**
     * 批量删除客户拜访
     *
     * @param ids 需要删除的客户拜访主键集合
     * @return 结果
     */
    public int deleteCustomerVisitByIds(Long[] ids);

    /**
     * 删除客户拜访信息
     *
     * @param id 客户拜访主键
     * @return 结果
     */
    public int deleteCustomerVisitById(Long id);
}
