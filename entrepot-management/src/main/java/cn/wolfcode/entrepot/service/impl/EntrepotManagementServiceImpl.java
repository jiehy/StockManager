package cn.wolfcode.entrepot.service.impl;

import java.util.List;

import cn.wolfcode.entrepot.domain.EntrepotManagement;
import cn.wolfcode.entrepot.domain.ItemInfoDTO;
import cn.wolfcode.entrepot.domain.ItemManagement;
import cn.wolfcode.entrepot.mapper.EntrepotManagementMapper;
import cn.wolfcode.entrepot.service.IEntrepotManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 仓库管理Service业务层处理
 * 
 * @author cjj
 * @date 2023-06-16
 */
@Service
public class EntrepotManagementServiceImpl implements IEntrepotManagementService
{
    @Autowired
    private EntrepotManagementMapper entrepotManagementMapper;

    /**
     * 查询仓库管理
     * 
     * @param id 仓库管理主键
     * @return 仓库管理
     */
    @Override
    public EntrepotManagement selectEntrepotManagementById(Long id)
    {
        return entrepotManagementMapper.selectEntrepotManagementById(id);
    }

    /**
     * 查询仓库管理列表
     * 
     * @param entrepotManagement 仓库管理
     * @return 仓库管理
     */
    @Override
    public List<EntrepotManagement> selectEntrepotManagementList(EntrepotManagement entrepotManagement)
    {
        return entrepotManagementMapper.selectEntrepotManagementList(entrepotManagement);
    }

    /**
     * 新增仓库管理
     * 
     * @param entrepotManagement 仓库管理
     * @return 结果
     */
    @Override
    public int insertEntrepotManagement(EntrepotManagement entrepotManagement)
    {
        if (entrepotManagement == null) {
            throw new RuntimeException ("参数不合法");
        }
        return entrepotManagementMapper.insertEntrepotManagement(entrepotManagement);
    }

    /**
     * 修改仓库管理
     * 
     * @param entrepotManagement 仓库管理
     * @return 结果
     */
    @Override
    public int updateEntrepotManagement(EntrepotManagement entrepotManagement)
    {
        if (entrepotManagement == null) {
            throw new RuntimeException ("参数不合法");
        }
        EntrepotManagement management = entrepotManagementMapper.selectEntrepotManagementById (entrepotManagement.getId ());
        if (management == null) {
            throw new RuntimeException ("参数不合法");
        }
        return entrepotManagementMapper.updateEntrepotManagement(entrepotManagement);
    }

    /**
     * 批量删除仓库管理
     * 
     * @param ids 需要删除的仓库管理主键
     * @return 结果
     */
    @Override
    public int deleteEntrepotManagementByIds(Long[] ids)
    {
        return entrepotManagementMapper.deleteEntrepotManagementByIds(ids);
    }

    /**
     * 删除仓库管理信息
     * 
     * @param id 仓库管理主键
     * @return 结果
     */
    @Override
    public int deleteEntrepotManagementById(Long id)
    {
        Assert.notNull (id,"参数不合法!");
        List<ItemInfoDTO> itemInfos = entrepotManagementMapper.selectEntrepotByInfoId (id);
        if (itemInfos.size ()!= 0) {
            throw new RuntimeException ("管理表还有物品，不能删除仓库！");
        }
        List<ItemManagement> itemManagements = entrepotManagementMapper.selectEntrepotByManagementId (id);
        if (itemManagements.size () != 0) {
            throw new RuntimeException ("信息表还有物品，不能删除仓库！");
        }
        return entrepotManagementMapper.deleteEntrepotManagementById(id);
    }
}
