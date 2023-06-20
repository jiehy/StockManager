package cn.wolfcode.warehouse.controller;

import cn.wolfcode.common.annotation.Log;
import cn.wolfcode.common.core.controller.BaseController;
import cn.wolfcode.common.core.domain.AjaxResult;
import cn.wolfcode.common.core.page.TableDataInfo;
import cn.wolfcode.common.enums.BusinessType;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.warehouse.domain.InboundOutboundManagement;
import cn.wolfcode.warehouse.service.IInboundOutboundManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 出入库管理Controller
 * 
 * @author wolfcode
 * @date 2023-06-17
 */
@RestController
@RequestMapping("/warehouse/storeroom")
public class InboundOutboundManagementController extends BaseController
{
    @Autowired
    private IInboundOutboundManagementService inboundOutboundManagementService;

    /**
     * 查询出入库管理列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:storeroom:list')")
    @GetMapping("/list")
    public TableDataInfo list(InboundOutboundManagement inboundOutboundManagement)
    {
        startPage();
        List<InboundOutboundManagement> list = inboundOutboundManagementService.selectInboundOutboundManagementList(inboundOutboundManagement);
        return getDataTable(list);
    }

    /**
     * 导出出入库管理列表
     */
    @PreAuthorize("@ss.hasPermi('warehouse:storeroom:export')")
    @Log(title = "出入库管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, InboundOutboundManagement inboundOutboundManagement)
    {
        List<InboundOutboundManagement> list = inboundOutboundManagementService.selectInboundOutboundManagementList(inboundOutboundManagement);
        ExcelUtil<InboundOutboundManagement> util = new ExcelUtil<InboundOutboundManagement>(InboundOutboundManagement.class);
        util.exportExcel(response, list, "出入库管理数据");
    }

    /**
     * 获取出入库管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('warehouse:storeroom:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(inboundOutboundManagementService.selectInboundOutboundManagementById(id));
    }

    /**
     * 新增出入库管理
     */
    @PreAuthorize("@ss.hasPermi('warehouse:storeroom:add')")
    @Log(title = "出入库管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody InboundOutboundManagement inboundOutboundManagement)
    {
        return toAjax(inboundOutboundManagementService.insertInboundOutboundManagement(inboundOutboundManagement));
    }

    /**
     * 修改出入库管理
     */
    @PreAuthorize("@ss.hasPermi('warehouse:storeroom:edit')")
    @Log(title = "出入库管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody InboundOutboundManagement inboundOutboundManagement)
    {
        return toAjax(inboundOutboundManagementService.updateInboundOutboundManagement(inboundOutboundManagement));
    }

    /**
     * 删除出入库管理
     */
    @PreAuthorize("@ss.hasPermi('warehouse:storeroom:remove')")
    @Log(title = "出入库管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(inboundOutboundManagementService.deleteInboundOutboundManagementByIds(ids));
    }

    /**
     * 出入库管理作废
     */
    @PreAuthorize("@ss.hasPermi('warehouse:storeroom:invalid')")
    @Log(title = "出入库管理", businessType = BusinessType.DELETE)
    @PutMapping("/{id}")
    public AjaxResult invalid(@PathVariable Long id) {
        return toAjax(inboundOutboundManagementService.invalid(id));
    }
}
