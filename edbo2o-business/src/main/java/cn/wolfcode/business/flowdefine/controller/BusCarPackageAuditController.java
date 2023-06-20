package cn.wolfcode.business.flowdefine.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.wolfcode.business.flowdefine.domain.vo.BusPackageReviewVO;
import cn.wolfcode.common.utils.ServletUtils;
import org.activiti.engine.HistoryService;
import org.apache.commons.io.IOUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.wolfcode.common.annotation.Log;
import cn.wolfcode.common.core.controller.BaseController;
import cn.wolfcode.common.core.domain.AjaxResult;
import cn.wolfcode.common.enums.BusinessType;
import cn.wolfcode.business.flowdefine.domain.BusCarPackageAudit;
import cn.wolfcode.business.flowdefine.service.IBusCarPackageAuditService;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.common.core.page.TableDataInfo;

/**
 * 套餐审核Controller
 * 
 * @author wolfcode
 * @date 2023-06-04
 */
@RestController
@RequestMapping("/flowdefine/audit")
public class BusCarPackageAuditController extends BaseController
{
    @Autowired
    private IBusCarPackageAuditService busCarPackageAuditService;

    /**
     * 查询套餐审核列表
     */
    @PreAuthorize("@ss.hasPermi('flowdefine:audit:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusCarPackageAudit busCarPackageAudit)
    {
        startPage();
        List<BusCarPackageAudit> list = busCarPackageAuditService.selectBusCarPackageAuditList(busCarPackageAudit);
        return getDataTable(list);
    }

    /**
     * 导出套餐审核列表
     */
    @PreAuthorize("@ss.hasPermi('flowdefine:audit:export')")
    @Log(title = "套餐审核", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusCarPackageAudit busCarPackageAudit)
    {
        List<BusCarPackageAudit> list = busCarPackageAuditService.selectBusCarPackageAuditList(busCarPackageAudit);
        ExcelUtil<BusCarPackageAudit> util = new ExcelUtil<BusCarPackageAudit>(BusCarPackageAudit.class);
        util.exportExcel(response, list, "套餐审核数据");
    }

    /**
     * 获取套餐审核详细信息
     */
    @PreAuthorize("@ss.hasPermi('flowdefine:audit:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busCarPackageAuditService.selectBusCarPackageAuditById(id));
    }

    /**
     * 新增套餐审核
     */
    @PreAuthorize("@ss.hasPermi('flowdefine:audit:add')")
    @Log(title = "套餐审核", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusCarPackageAudit busCarPackageAudit)
    {
        return toAjax(busCarPackageAuditService.insertBusCarPackageAudit(busCarPackageAudit));
    }

    /**
     * 修改套餐审核
     */
    @PreAuthorize("@ss.hasPermi('flowdefine:audit:edit')")
    @Log(title = "套餐审核", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusCarPackageAudit busCarPackageAudit)
    {
        return toAjax(busCarPackageAuditService.updateBusCarPackageAudit(busCarPackageAudit));
    }

    /**
     * 删除套餐审核
     */
    @PreAuthorize("@ss.hasPermi('flowdefine:audit:remove')")
    @Log(title = "套餐审核", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busCarPackageAuditService.deleteBusCarPackageAuditByIds(ids));
    }

    /**
     * 进度查看
     */
    @PreAuthorize("@ss.hasPermi('flowdefine:audit:progress')")
    @Log(title = "套餐审核", businessType = BusinessType.OTHER)
    @GetMapping("/progressAudit/{id}")
    public void progressAudit(@PathVariable Long id) throws IOException {
        InputStream inputStream = busCarPackageAuditService.progressAudit(id);
        IOUtils.copy(inputStream, ServletUtils.getResponse().getOutputStream());
    }

    /**
     * test
     */
    @PreAuthorize("@ss.hasPermi('flowdefine:audit:test')")
    @Log(title = "套餐审核", businessType = BusinessType.OTHER)
    @GetMapping("/test")
    public AjaxResult progressAudit() {
        busCarPackageAuditService.test();
        return AjaxResult.success();
    }

    /**
     * 审批历史
     */
    @PreAuthorize("@ss.hasPermi('flowdefine:audit:history')")
    @Log(title = "套餐审核", businessType = BusinessType.OTHER)
    @GetMapping("/history/{id}")
    public AjaxResult history(@PathVariable Long id) {
        return AjaxResult.success(busCarPackageAuditService.queryHistoryById(id));
    }

    /**
     * 撤销审批
     */
    @PreAuthorize("@ss.hasPermi('flowdefine:audit:cancel')")
    @Log(title = "套餐审核", businessType = BusinessType.DELETE)
    @PostMapping("/cancel/{id}")
    public AjaxResult cancel(@PathVariable Long id) {
        return toAjax(busCarPackageAuditService.cancel(id));
    }

    /**
     * 我的待办列表
     */
    @PreAuthorize("@ss.hasPermi('flowdefine:audit:todoQuery')")
    @GetMapping("/todoQuery")
    public TableDataInfo todo(BusCarPackageAudit busCarPackageAudit)
    {
        startPage();
        return getDataTable(busCarPackageAuditService.queryTodo(busCarPackageAudit));
    }

    /**
     * 我的待办审核列表
     */
    @PreAuthorize("@ss.hasPermi('flowdefine:audit:packagereview')")
    @Log(title = "套餐审核", businessType = BusinessType.OTHER)
    @PostMapping("/packageReview")
    public AjaxResult packageReview(@RequestBody BusPackageReviewVO packageReviewVO)
    {
        busCarPackageAuditService.packageReview(packageReviewVO);
        return AjaxResult.success();
    }

    /**
     * 我的已办列表
     */
    @PreAuthorize("@ss.hasPermi('flowdefine:audit:done')")
    @Log(title = "套餐审核", businessType = BusinessType.OTHER)
    @GetMapping("/doneAudit")
    public TableDataInfo done(BusCarPackageAudit busCarPackageAudit)
    {
        startPage();
        return getDataTable(busCarPackageAuditService.queryDone(busCarPackageAudit));
    }
}
