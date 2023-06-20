package cn.wolfcode.goods.service.impl;

import cn.wolfcode.goods.domain.ItemInfo;
import cn.wolfcode.goods.mapper.ItemInfoMapper;
import cn.wolfcode.goods.service.IItemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 物品基本信息Service业务层处理
 * 
 * @author lxy
 * @date 2023-06-17
 */
@Service
public class ItemInfoServiceImpl implements IItemInfoService 
{
    @Autowired
    private ItemInfoMapper itemInfoMapper;

    /**
     * 查询物品基本信息
     * 
     * @param id 物品基本信息主键
     * @return 物品基本信息
     */
    @Override
    public ItemInfo selectItemInfoById(Long id)
    {
        return itemInfoMapper.selectItemInfoById(id);
    }

    /**
     * 查询物品基本信息列表
     * 
     * @param itemInfo 物品基本信息
     * @return 物品基本信息
     */
    @Override
    public List<ItemInfo> selectItemInfoList(ItemInfo itemInfo)
    {
        if (itemInfo == null) {
            throw new RuntimeException ("参数异常");
        }
        return itemInfoMapper.selectItemInfoList(itemInfo);
    }

    /**
     * 新增物品基本信息
     * 
     * @param itemInfo 物品基本信息
     * @return 结果
     */
    @Override
    public int insertItemInfo(ItemInfo itemInfo)
    {
        return itemInfoMapper.insertItemInfo(itemInfo);
    }

    /**
     * 修改物品基本信息
     * 
     * @param itemInfo 物品基本信息
     * @return 结果
     */
    @Override
    public int updateItemInfo(ItemInfo itemInfo)
    {
        return itemInfoMapper.updateItemInfo(itemInfo);
    }

    /**
     * 批量删除物品基本信息
     * 
     * @param ids 需要删除的物品基本信息主键
     * @return 结果
     */
    @Override
    public int deleteItemInfoByIds(Long[] ids)
    {
        return itemInfoMapper.deleteItemInfoByIds(ids);
    }

    /**
     * 删除物品基本信息信息
     *
     * @param id 物品基本信息主键
     * @return 结果
     */
    @Override
    public int deleteItemInfoById(Long id) {
        return itemInfoMapper.deleteItemInfoById(id);
    }

    @Override
    public List<ItemInfo> AllList() {

        return itemInfoMapper.selectItemInfoList();
    }
}
