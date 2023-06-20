package cn.wolfcode.customer.visit.controller;

import cn.wolfcode.customer.visit.domain.CustomerVisit;
import cn.wolfcode.customer.visit.service.ICustomerVisitService;
import cn.wolfcode.common.annotation.Log;
import cn.wolfcode.common.core.controller.BaseController;
import cn.wolfcode.common.core.domain.AjaxResult;
import cn.wolfcode.common.core.page.TableDataInfo;
import cn.wolfcode.common.enums.BusinessType;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import cn.wolfcode.customer.visit.domain.vo.CustomerVisitVO;

/**
 * 客户拜访Controller
 *
 * @author HH
 * @date 2023-06-16
 */
@RestController
@RequestMapping("/customer/visit")
public class CustomerVisitController extends BaseController {
    @Autowired
    private ICustomerVisitService customerVisitService;

    /**
     * 查询客户拜访列表
     */
    @PreAuthorize("@ss.hasPermi('customer:visit:list')")
    @GetMapping("/list")
    public TableDataInfo list(CustomerVisit customerVisit) {
        startPage();
        List<CustomerVisit> list = customerVisitService.selectCustomerVisitList(customerVisit);
        return getDataTable(list);
    }

    /**
     * 导出客户拜访列表
     */
    @PreAuthorize("@ss.hasPermi('customer:visit:export')")
    @Log(title = "客户拜访", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, CustomerVisit customerVisit) {
        List<CustomerVisit> list = customerVisitService.selectCustomerVisitList(customerVisit);
        ExcelUtil<CustomerVisit> util = new ExcelUtil<CustomerVisit>(CustomerVisit.class);
        util.exportExcel(response, list, "客户拜访数据");
    }

    /**
     * 获取客户拜访详细信息
     */
    @PreAuthorize("@ss.hasPermi('customer:visit:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(customerVisitService.selectCustomerVisitById(id));
    }

    /**
     * 新增客户拜访
     */
    @PreAuthorize("@ss.hasPermi('customer:visit:add')")
    @Log(title = "客户拜访", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CustomerVisitVO customerVisit) {

        return toAjax(customerVisitService.insertCustomerVisit(customerVisit));
    }

    /**
     * 修改客户拜访
     */
    @PreAuthorize("@ss.hasPermi('customer:visit:edit')")
    @Log(title = "客户拜访", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CustomerVisit customerVisit) {
        return toAjax(customerVisitService.updateCustomerVisit(customerVisit));
    }

    /**
     * 删除客户拜访
     */
    @PreAuthorize("@ss.hasPermi('customer:visit:remove')")
    @Log(title = "客户拜访", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(customerVisitService.deleteCustomerVisitByIds(ids));
    }
}
