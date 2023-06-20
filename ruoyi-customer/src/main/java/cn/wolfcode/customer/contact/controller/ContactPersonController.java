package cn.wolfcode.customer.contact.controller;



import cn.wolfcode.customer.contact.domain.vo.ContactPersonVO;
import cn.wolfcode.common.annotation.Log;
import cn.wolfcode.common.core.controller.BaseController;
import cn.wolfcode.common.core.domain.AjaxResult;
import cn.wolfcode.common.core.page.TableDataInfo;
import cn.wolfcode.common.enums.BusinessType;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.customer.contact.domain.ContactPerson;
import cn.wolfcode.customer.contact.service.IContactPersonService;
import cn.wolfcode.customer.information.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 客户联系人Controller
 *
 * @author HH
 * @date 2023-06-16
 */
@RestController
@RequestMapping("/customer/contact")
public class ContactPersonController extends BaseController {
    @Autowired
    private IContactPersonService contactPersonService;
    @Autowired
    private ICustomerService customerService;

    /**
     * 查询客户联系人列表
     */
    @PreAuthorize("@ss.hasPermi('customer:contact:list')")
    @GetMapping("/list")
    public TableDataInfo list(ContactPerson contactPerson) {
        startPage();
        List<ContactPerson> list = contactPersonService.selectContactPersonList(contactPerson);
        return getDataTable(list);
    }

    /**
     * 导出客户联系人列表
     */
    @PreAuthorize("@ss.hasPermi('customer:contact:export')")
    @Log(title = "客户联系人", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ContactPerson contactPerson) {
        List<ContactPerson> list = contactPersonService.selectContactPersonList(contactPerson);
        ExcelUtil<ContactPerson> util = new ExcelUtil<ContactPerson>(ContactPerson.class);
        util.exportExcel(response, list, "客户联系人数据");
    }

    /**
     * 获取客户联系人详细信息
     */
    @PreAuthorize("@ss.hasPermi('customer:contact:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(contactPersonService.selectContactPersonById(id));
    }

    /**
     * 新增客户联系人
     */
    @PreAuthorize("@ss.hasPermi('customer:contact:add')")
    @Log(title = "客户联系人", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ContactPersonVO contactPerson) {
        return toAjax(contactPersonService.insertContactPerson(contactPerson));
    }

    /**
     * 修改客户联系人
     */
    @PreAuthorize("@ss.hasPermi('customer:contact:edit')")
    @Log(title = "客户联系人", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ContactPersonVO contactPerson) {
        return toAjax(contactPersonService.updateContactPerson(contactPerson));
    }

    /**
     * 删除客户联系人
     */
    @PreAuthorize("@ss.hasPermi('customer:contact:remove')")
    @Log(title = "客户联系人", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(contactPersonService.deleteContactPersonByIds(ids));
    }


    /**
     * 查询所属客户列表
     */
    @PreAuthorize("@ss.hasPermi('customer:contact:customerList')")
    @GetMapping("/customerList")
    public AjaxResult customerList() {

        List<String> list = customerService.selectCustomerListByname();
        return AjaxResult.success(list);
    }

    /**
     * 查询联系人列表
     */
    @PreAuthorize("@ss.hasPermi('customer:contact:contactList')")
    @GetMapping("/contactList")
    public AjaxResult contactList(String customerName) {

        List<String> list = contactPersonService.selectCustomerListName(customerName);
        return AjaxResult.success(list);
    }
}
