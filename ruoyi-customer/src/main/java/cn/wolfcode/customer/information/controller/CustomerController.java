package cn.wolfcode.customer.information.controller;

import cn.wolfcode.customer.information.domain.vo.CustomerVO;
import cn.wolfcode.common.annotation.Log;
import cn.wolfcode.common.core.controller.BaseController;
import cn.wolfcode.common.core.domain.AjaxResult;
import cn.wolfcode.common.core.page.TableDataInfo;
import cn.wolfcode.common.enums.BusinessType;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.customer.information.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;;
import cn.wolfcode.customer.information.domain.Customer;


/**
 * 客户基本信息管理Controller
 *
 * @author HH
 * @date 2023-06-16
 */
@RestController
@RequestMapping("/customer/information")
public class CustomerController extends BaseController {
    @Autowired
    private ICustomerService customerService;

    /**
     * 查询客户基本信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('customer:information:list')")
    @GetMapping("/list")
    public TableDataInfo list(Customer customer) {
        startPage();
        List<Customer> list = customerService.selectCustomerList(customer);
        return getDataTable(list);
    }

    /**
     * 导出客户基本信息管理列表
     */
    @PreAuthorize("@ss.hasPermi('customer:information:export')")
    @Log(title = "客户基本信息管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Customer customer) {
        List<Customer> list = customerService.selectCustomerList(customer);
        ExcelUtil<Customer> util = new ExcelUtil<Customer>(Customer.class);
        util.exportExcel(response, list, "客户基本信息管理数据");
    }

    /**
     * 获取客户基本信息管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('customer:information:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(customerService.selectCustomerById(id));
    }

    /**
     * 新增客户基本信息管理
     */
    @PreAuthorize("@ss.hasPermi('customer:information:add')")
    @Log(title = "客户基本信息管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody CustomerVO customer) {
        return toAjax(customerService.insertCustomer(customer));
    }

    /**
     * 修改客户基本信息管理
     */
    @PreAuthorize("@ss.hasPermi('customer:information:edit')")
    @Log(title = "客户基本信息管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody CustomerVO customer) {

        return toAjax(customerService.updateCustomer(customer));
    }

    /**
     * 删除客户基本信息管理
     */
    @PreAuthorize("@ss.hasPermi('customer:information:remove')")
    @Log(title = "客户基本信息管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(customerService.deleteCustomerByIds(ids));
    }
}
