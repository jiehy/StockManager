package cn.wolfcode.contract.controller;

import cn.wolfcode.common.annotation.Log;
import cn.wolfcode.common.core.controller.BaseController;
import cn.wolfcode.common.core.domain.AjaxResult;
import cn.wolfcode.common.core.page.TableDataInfo;
import cn.wolfcode.common.enums.BusinessType;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.contract.domain.ContractManagement;
import cn.wolfcode.contract.domain.dto.ContractManagementReqDTO;
import cn.wolfcode.contract.service.IContractManagementService;
import cn.wolfcode.customer.contact.service.IContactPersonService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 合同管理Controller
 *
 * @author YL
 * @date 2023-06-16
 */
@RestController
@RequestMapping("/business/contract")
public class ContractManagementController extends BaseController {
    @Autowired
    private IContractManagementService contractManagementService;

    @Autowired
    private IContactPersonService contactPersonService;

    /**
     * 查询合同管理列表
     */
    @PreAuthorize("@ss.hasPermi('business:contractManagement:list')")
    @GetMapping("/list")
    public TableDataInfo list(ContractManagement contractManagement) {
        startPage();
        List<ContractManagement> list = contractManagementService.selectContractManagementList(contractManagement);
        return getDataTable(list);
    }

    /**
     * 导出合同管理列表
     */
    @PreAuthorize("@ss.hasPermi('business:contractManagement:export')")
    @Log(title = "合同管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ContractManagement contractManagement) {
        List<ContractManagement> list = contractManagementService.selectContractManagementList(contractManagement);
        ExcelUtil<ContractManagement> util = new ExcelUtil<ContractManagement>(ContractManagement.class);
        util.exportExcel(response, list, "合同管理数据");
    }

    /**
     * 获取合同管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('business:contractManagement:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(contractManagementService.selectContractManagementById(id));
    }

    /**
     * 审批通过合同
     */
    @PreAuthorize("@ss.hasPermi('business:contractManagement:query')")
    @GetMapping(value = "/auditSuccess/{id}")
    public AjaxResult auditSuccess(@PathVariable Long id) {
        contractManagementService.auditSuccess(id);
        return AjaxResult.success("审批成功");
    }

    /**
     * 审批不通过合同
     */
    @PreAuthorize("@ss.hasPermi('business:contractManagement:query')")
    @PostMapping(value = "/auditPass/{id}")
    public AjaxResult auditPass(@PathVariable Long id) {
        contractManagementService.auditPass(id);
        return AjaxResult.success("审批成功");
    }

    /**
     * 确认盖章
     */
    @PreAuthorize("@ss.hasPermi('business:contractManagement:query')")
    @GetMapping(value = "/toStamp/{id}")
    public AjaxResult toStamp(@PathVariable Long id) {
        contractManagementService.toStamp(id);
        return AjaxResult.success("审批成功");
    }

    /**
     * 合同作废
     */
    @PreAuthorize("@ss.hasPermi('business:contractManagement:query')")
    @GetMapping(value = "/invalid/{id}")
    public AjaxResult invalid(@PathVariable Long id) {
        contractManagementService.invalid(id);
        return AjaxResult.success("审批成功");
    }

    /**
     * 查询客户列表
     */
    @PreAuthorize("@ss.hasPermi('business:contractManagement:query')")
    @GetMapping(value = "/getCustomerList")
    public AjaxResult getCustomerList() {
        return AjaxResult.success(contactPersonService.selectContactPersonList(null));
    }


    /**
     * 新增合同管理
     */
    @PreAuthorize("@ss.hasPermi('business:contractManagement:add')")
    @Log(title = "合同管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(ContractManagementReqDTO contractManagementReqDTO) throws IOException {
        return toAjax(contractManagementService.insertContractManagement(contractManagementReqDTO));
    }

    /**
     * 下载电子附件
     */
    @PreAuthorize("@ss.hasPermi('business:contractManagement:add')")
    @Log(title = "合同管理", businessType = BusinessType.INSERT)
    @GetMapping("/download/{id}")
    public void download(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response) throws Exception {
         ContractManagement contractManagement = contractManagementService.selectContractManagementById(id);
        String[] split = contractManagement.getElectronicAccessories().split("/");
        String name = split[split.length - 1];
        response.setContentType("application/x-msdownload");
        response.setHeader("Content-Disposition", "attachment;filename=\"" + name + "\"");

        try (RandomAccessFile randomAccessFile = new RandomAccessFile(new File(System.getProperty("user.dir") + "/" + contractManagement.getElectronicAccessories()), "rw")) {
            FileChannel channel = randomAccessFile.getChannel();
            System.out.println(channel.size());
            channel.transferTo(0, channel.size(),
                    Channels.newChannel(response.getOutputStream()));
        }
    }

    /**
     * 修改合同管理
     */
    @PreAuthorize("@ss.hasPermi('business:contractManagement:edit')")
    @Log(title = "合同管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(ContractManagementReqDTO contractManagementReqDTO) throws Exception {
        return toAjax(contractManagementService.updateContractManagement(contractManagementReqDTO));
    }

    /**
     * 删除合同管理
     */
    @PreAuthorize("@ss.hasPermi('business:contractManagement:remove')")
    @Log(title = "合同管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(contractManagementService.deleteContractManagementByIds(ids));
    }
}
