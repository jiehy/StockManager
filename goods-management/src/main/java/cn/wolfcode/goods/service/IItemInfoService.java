package cn.wolfcode.goods.service;

import cn.wolfcode.goods.domain.ItemInfo;

import java.util.List;

/**
 * 物品基本信息Service接口
 * 
 * @author lxy
 * @date 2023-06-17
 */
public interface IItemInfoService 
{
    /**
     * 查询物品基本信息
     * 
     * @param id 物品基本信息主键
     * @return 物品基本信息
     */
    public ItemInfo selectItemInfoById(Long id);

    /**
     * 查询物品基本信息列表
     * 
     * @param itemInfo 物品基本信息
     * @return 物品基本信息集合
     */
    public List<ItemInfo> selectItemInfoList(ItemInfo itemInfo);

    /**
     * 新增物品基本信息
     * 
     * @param itemInfo 物品基本信息
     * @return 结果
     */
    public int insertItemInfo(ItemInfo itemInfo);

    /**
     * 修改物品基本信息
     * 
     * @param itemInfo 物品基本信息
     * @return 结果
     */
    public int updateItemInfo(ItemInfo itemInfo);

    /**
     * 批量删除物品基本信息
     * 
     * @param ids 需要删除的物品基本信息主键集合
     * @return 结果
     */
    public int deleteItemInfoByIds(Long[] ids);

    /**
     * 删除物品基本信息信息
     *
     * @param id 物品基本信息主键
     * @return 结果
     */
    public int deleteItemInfoById(Long id);

    List<ItemInfo> AllList();
}
