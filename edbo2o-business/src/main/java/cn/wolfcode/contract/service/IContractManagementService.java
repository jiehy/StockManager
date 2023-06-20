package cn.wolfcode.contract.service;

import cn.wolfcode.contract.domain.ContractManagement;
import cn.wolfcode.contract.domain.dto.ContractManagementReqDTO;
import com.mybatisflex.core.service.IService;

import java.io.IOException;
import java.util.List;

/**
 * 合同管理Service接口
 * 
 * @author YL
 * @date 2023-06-16
 */
public interface IContractManagementService extends IService<ContractManagement>
{
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
    public int insertContractManagement(ContractManagementReqDTO contractManagement) throws IOException;

    /**
     * 修改合同管理
     *
     * @param contractManagementReqDTO 合同管理
     * @return 结果
     */
    public int updateContractManagement(ContractManagementReqDTO contractManagementReqDTO) throws Exception;

    /**
     * 批量删除合同管理
     * 
     * @param ids 需要删除的合同管理主键集合
     * @return 结果
     */
    public int deleteContractManagementByIds(Long[] ids);

    /**
     * 删除合同管理信息
     * 
     * @param id 合同管理主键
     * @return 结果
     */
    public int deleteContractManagementById(Long id);

    void auditSuccess(Long id);

    void auditPass(Long id);

    void toStamp(Long id);

    void invalid(Long id);

}
