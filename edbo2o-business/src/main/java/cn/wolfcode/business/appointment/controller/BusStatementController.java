package cn.wolfcode.business.appointment.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import cn.wolfcode.business.appointment.domain.vo.BusStatementVO;
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
import cn.wolfcode.business.appointment.domain.BusStatement;
import cn.wolfcode.business.appointment.service.IBusStatementService;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.common.core.page.TableDataInfo;

/**
 * 结算单Controller
 * 
 * @author wolfcode
 * @date 2023-05-31
 */
@RestController
@RequestMapping("/appointment/statement")
public class BusStatementController extends BaseController
{
    @Autowired
    private IBusStatementService busStatementService;

    /**
     * 查询结算单列表
     */
    @PreAuthorize("@ss.hasPermi('appointment:statement:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusStatement busStatement)
    {
        startPage();
        List<BusStatement> list = busStatementService.selectBusStatementList(busStatement);
        return getDataTable(list);
    }

    /**
     * 导出结算单列表
     */
    @PreAuthorize("@ss.hasPermi('appointment:statement:export')")
    @Log(title = "结算单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusStatement busStatement)
    {
        List<BusStatement> list = busStatementService.selectBusStatementList(busStatement);
        ExcelUtil<BusStatement> util = new ExcelUtil<BusStatement>(BusStatement.class);
        util.exportExcel(response, list, "结算单数据");
    }

    /**
     * 获取结算单详细信息
     */
    @PreAuthorize("@ss.hasPermi('appointment:statement:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busStatementService.selectBusStatementById(id));
    }

    /**
     * 新增结算单
     */
    @PreAuthorize("@ss.hasPermi('appointment:statement:add')")
    @Log(title = "结算单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody @Valid BusStatementVO busStatementVO)
    {
        return toAjax(busStatementService.insertBusStatement(busStatementVO));
    }

    /**
     * 修改结算单
     */
    @PreAuthorize("@ss.hasPermi('appointment:statement:edit')")
    @Log(title = "结算单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusStatementVO busStatementVO)
    {
        return toAjax(busStatementService.updateBusStatement(busStatementVO));
    }

    /**
     * 删除结算单
     */
    @PreAuthorize("@ss.hasPermi('appointment:statement:remove')")
    @Log(title = "结算单", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)
    {
        return toAjax(busStatementService.deleteBusStatementById(id));
    }

    /**
     * 同步信息结算单
     */
    @PreAuthorize("@ss.hasPermi('appointment:statement:sync')")
    @Log(title = "结算单", businessType = BusinessType.DELETE)
	@PostMapping("/sync")
    public AjaxResult sync(@RequestBody BusStatement BusStatement)
    {
        busStatementService.sync(BusStatement);
        return AjaxResult.success();
    }
}
