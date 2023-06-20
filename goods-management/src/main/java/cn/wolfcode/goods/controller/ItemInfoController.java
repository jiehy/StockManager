package cn.wolfcode.goods.controller;

import cn.wolfcode.common.annotation.Log;
import cn.wolfcode.common.core.controller.BaseController;
import cn.wolfcode.common.core.domain.AjaxResult;
import cn.wolfcode.common.core.page.TableDataInfo;
import cn.wolfcode.common.enums.BusinessType;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.goods.domain.ItemInfo;
import cn.wolfcode.goods.service.IItemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 物品基本信息Controller
 * 
 * @author lxy
 * @date 2023-06-17
 */
@RestController
@RequestMapping("/goods/management")
public class ItemInfoController extends BaseController
{
    @Autowired
    private IItemInfoService itemInfoService;

    /**
     * 查询物品基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('goods:management:list')")
    @GetMapping("/list")
    public TableDataInfo list(ItemInfo itemInfo) {
        startPage();
        List<ItemInfo> list = itemInfoService.selectItemInfoList(itemInfo);
        return getDataTable(list);
    }

    /**
     * 查询物品基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('goods:management:list')")
    @GetMapping("/AllList")
    public AjaxResult AllList() {
        return AjaxResult.success(itemInfoService.AllList());
    }

    /**
     * 导出物品基本信息列表
     */
    @PreAuthorize("@ss.hasPermi('goods:management:export')")
    @Log(title = "物品基本信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ItemInfo itemInfo) {
        List<ItemInfo> list = itemInfoService.selectItemInfoList(itemInfo);
        ExcelUtil<ItemInfo> util = new ExcelUtil<ItemInfo>(ItemInfo.class);
        util.exportExcel(response, list, "物品基本信息数据");
    }

    /**
     * 获取物品基本信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('goods:management:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(itemInfoService.selectItemInfoById(id));
    }

    /**
     * 新增物品基本信息
     */
    @PreAuthorize("@ss.hasPermi('goods:management:add')")
    @Log(title = "物品基本信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ItemInfo itemInfo)
    {
        return toAjax(itemInfoService.insertItemInfo(itemInfo));
    }

    /**
     * 修改物品基本信息
     */
    @PreAuthorize("@ss.hasPermi('goods:management:edit')")
    @Log(title = "物品基本信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ItemInfo itemInfo)
    {
        return toAjax(itemInfoService.updateItemInfo(itemInfo));
    }

    /**
     * 删除物品基本信息
     */
    @PreAuthorize("@ss.hasPermi('goods:management:remove')")
    @Log(title = "物品基本信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(itemInfoService.deleteItemInfoByIds(ids));
    }
}
