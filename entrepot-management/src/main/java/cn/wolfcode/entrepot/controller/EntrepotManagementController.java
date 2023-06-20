package cn.wolfcode.entrepot.controller;

import java.util.List;

import cn.wolfcode.entrepot.service.IEntrepotManagementService;
import cn.wolfcode.common.annotation.Log;
import cn.wolfcode.common.core.controller.BaseController;
import cn.wolfcode.common.core.domain.AjaxResult;
import cn.wolfcode.common.core.page.TableDataInfo;
import cn.wolfcode.common.enums.BusinessType;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.entrepot.domain.EntrepotManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;


/**
 * 仓库管理Controller
 * 
 * @author cjj
 * @date 2023-06-16
 */
@RestController
@RequestMapping("/entrepot/management")
public class EntrepotManagementController extends BaseController
{
    @Autowired
    private IEntrepotManagementService entrepotManagementService;

    /**
     * 查询仓库管理列表
     */
    @PreAuthorize("@ss.hasPermi('entrepot:management:list')")
    @GetMapping("/list")
    public TableDataInfo list(EntrepotManagement entrepotManagement)
    {
        startPage();
        List<EntrepotManagement> list = entrepotManagementService.selectEntrepotManagementList(entrepotManagement);
        return getDataTable(list);
    }

    /**
     * 导出仓库管理列表
     */
    @PreAuthorize("@ss.hasPermi('entrepot:management:export')")
    @Log(title = "仓库管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EntrepotManagement entrepotManagement)
    {
        List<EntrepotManagement> list = entrepotManagementService.selectEntrepotManagementList(entrepotManagement);
        ExcelUtil<EntrepotManagement> util = new ExcelUtil<EntrepotManagement>(EntrepotManagement.class);
        util.exportExcel(response, list, "仓库管理数据");
    }

    /**
     * 获取仓库管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('entrepot:management:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(entrepotManagementService.selectEntrepotManagementById(id));
    }

    /**
     * 新增仓库管理
     */
    @PreAuthorize("@ss.hasPermi('entrepot:management:add')")
    @Log(title = "仓库管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EntrepotManagement entrepotManagement)
    {
        return toAjax(entrepotManagementService.insertEntrepotManagement(entrepotManagement));
    }

    /**
     * 修改仓库管理
     */
    @PreAuthorize("@ss.hasPermi('entrepot:management:edit')")
    @Log(title = "仓库管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EntrepotManagement entrepotManagement)
    {
        return toAjax(entrepotManagementService.updateEntrepotManagement(entrepotManagement));
    }

    /**
     * 删除仓库管理
     */
    @PreAuthorize("@ss.hasPermi('entrepot:management:remove')")
    @Log(title = "仓库管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)
    {
        return toAjax(entrepotManagementService.deleteEntrepotManagementById(id));
    }
}
