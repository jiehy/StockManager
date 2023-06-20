package cn.wolfcode.contract.mapper;


import cn.wolfcode.contract.domain.ContractManagement;
import com.mybatisflex.core.BaseMapper;

import java.util.List;

/**
 * 合同管理Mapper接口
 *
 * @author YL
 * @date 2023-06-16
 */
public interface ContractManagementMapper extends BaseMapper<ContractManagement> {
    /**
     * 查询合同管理
     *
     * @param id 合同管理主键
     * @return 合同管理
     */
    public ContractManagement selectContractManagementById(Long id);

    /**
     * 查询合同管理列表
     *
     * @param contractManagement 合同管理
     * @return 合同管理集合
     */
    public List<ContractManagement> selectContractManagementList(ContractManagement contractManagement);

    /**
     * 新增合同管理
     *
     * @param contractManagement 合同管理
     * @return 结果
     */
    public int insertContractManagement(ContractManagement contractManagement);

    /**
     * 修改合同管理
     *
     * @param contractManagement 合同管理
     * @return 结果
     */
    public int updateContractManagement(ContractManagement contractManagement);

    /**
     * 删除合同管理
     *
     * @param id 合同管理主键
     * @return 结果
     */
    public int deleteContractManagementById(Long id);

    /**
     * 批量删除合同管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteContractManagementByIds(Long[] ids);
}
