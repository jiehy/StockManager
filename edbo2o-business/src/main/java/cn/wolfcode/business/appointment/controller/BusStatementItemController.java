package cn.wolfcode.business.appointment.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import cn.wolfcode.business.appointment.domain.vo.BusStatementItemVO;
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
import cn.wolfcode.business.appointment.domain.BusStatementItem;
import cn.wolfcode.business.appointment.service.IBusStatementItemService;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.common.core.page.TableDataInfo;

/**
 * 结算单明细Controller
 * 
 * @author wolfcode
 * @date 2023-05-31
 */
@RestController
@RequestMapping("/appointment/statementItem")
public class BusStatementItemController extends BaseController
{
    @Autowired
    private IBusStatementItemService busStatementItemService;

    /**
     * 查询结算单明细列表
     */
    @PreAuthorize("@ss.hasPermi('appointment:statementItem:list')")
    @GetMapping
    public TableDataInfo list(BusStatementItem busStatementItem)
    {
        startPage();
        List<BusStatementItem> list = busStatementItemService.selectBusStatementItemList(busStatementItem);
        return getDataTable(list);
    }

    /**
     * 导出结算单明细列表
     */
    @PreAuthorize("@ss.hasPermi('appointment:statementItem:export')")
    @Log(title = "结算单明细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusStatementItem busStatementItem)
    {
        List<BusStatementItem> list = busStatementItemService.selectBusStatementItemList(busStatementItem);
        ExcelUtil<BusStatementItem> util = new ExcelUtil<BusStatementItem>(BusStatementItem.class);
        util.exportExcel(response, list, "结算单明细数据");
    }

    /**
     * 获取结算单明细详细信息
     */
    @PreAuthorize("@ss.hasPermi('appointment:statementItem:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busStatementItemService.selectBusStatementItemById(id));
    }

    /**
     * 新增结算单明细
     */
    @PreAuthorize("@ss.hasPermi('appointment:statementItem:add')")
    @Log(title = "结算单明细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody @Valid BusStatementItemVO busStatementItemVO)
    {
        return toAjax(busStatementItemService.insertBusStatementItem(busStatementItemVO));
    }

    /**
     * 支付结算单明细
     */
    @PreAuthorize("@ss.hasPermi('appointment:statementItem:pay')")
    @Log(title = "结算单明细", businessType = BusinessType.UPDATE)
    @PutMapping("/pay/{id}")
    public AjaxResult pay(@PathVariable Long id)
    {
        return toAjax(busStatementItemService.pay(id));
    }

    /**
     * 支付结算单明细
     */
    @PreAuthorize("@ss.hasPermi('appointment:statementItem:QRCode')")
    @Log(title = "结算单明细", businessType = BusinessType.UPDATE)
    @PutMapping("/QRCode")
    public AjaxResult QRCode()
    {
        return AjaxResult.success(busStatementItemService.QRCode());
    }
}
