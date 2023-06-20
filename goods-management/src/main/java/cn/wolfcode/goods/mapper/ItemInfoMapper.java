package cn.wolfcode.goods.mapper;

import cn.wolfcode.goods.domain.ItemInfo;

import java.util.List;

/**
 * 物品基本信息Mapper接口
 * 
 * @author lxy
 * @date 2023-06-17
 */
public interface ItemInfoMapper 
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
     * 删除物品基本信息
     * 
     * @param id 物品基本信息主键
     * @return 结果
     */
    public int deleteItemInfoById(Long id);

    /**
     * 批量删除物品基本信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteItemInfoByIds(Long[] ids);

    List<ItemInfo> selectItemInfoList();
}
