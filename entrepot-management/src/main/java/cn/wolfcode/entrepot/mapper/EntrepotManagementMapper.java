package cn.wolfcode.entrepot.mapper;

import java.util.List;

import cn.wolfcode.entrepot.domain.EntrepotManagement;
import cn.wolfcode.entrepot.domain.ItemInfoDTO;
import cn.wolfcode.entrepot.domain.ItemManagement;

/**
 * 仓库管理Mapper接口
 * 
 * @author cjj
 * @date 2023-06-16
 */
public interface EntrepotManagementMapper 
{
    /**
     * 查询仓库管理
     * 
     * @param id 仓库管理主键
     * @return 仓库管理
     */
    public EntrepotManagement selectEntrepotManagementById(Long id);

    /**
     * 查询仓库管理列表
     * 
     * @param entrepotManagement 仓库管理
     * @return 仓库管理集合
     */
    public List<EntrepotManagement> selectEntrepotManagementList(EntrepotManagement entrepotManagement);

    /**
     * 新增仓库管理
     * 
     * @param entrepotManagement 仓库管理
     * @return 结果
     */
    public int insertEntrepotManagement(EntrepotManagement entrepotManagement);

    /**
     * 修改仓库管理
     * 
     * @param entrepotManagement 仓库管理
     * @return 结果
     */
    public int updateEntrepotManagement(EntrepotManagement entrepotManagement);

    /**
     * 删除仓库管理
     * 
     * @param id 仓库管理主键
     * @return 结果
     */
    public int deleteEntrepotManagementById(Long id);

    /**
     * 批量删除仓库管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteEntrepotManagementByIds(Long[] ids);

    List<ItemInfoDTO> selectEntrepotByInfoId(Long id);

    List<ItemManagement> selectEntrepotByManagementId(Long id);
}
