package cn.wolfcode.business.flowdefine.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import cn.wolfcode.business.flowdefine.domain.vo.BusBpmnInfoVO;
import cn.wolfcode.common.utils.ServletUtils;
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
import cn.wolfcode.business.flowdefine.domain.BusBpmnInfo;
import cn.wolfcode.business.flowdefine.service.IBusBpmnInfoService;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 审核流程定义Controller
 * 
 * @author wolfcode
 * @date 2023-06-03
 */
@RestController
@RequestMapping("/flowdefine/bpmninfo")
public class BusBpmnInfoController extends BaseController
{
    @Autowired
    private IBusBpmnInfoService busBpmnInfoService;

    /**
     * 查询审核流程定义列表
     */
    @PreAuthorize("@ss.hasPermi('flowdefine:bpmninfo:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusBpmnInfo busBpmnInfo)
    {
        startPage();
        List<BusBpmnInfo> list = busBpmnInfoService.selectBusBpmnInfoList(busBpmnInfo);
        return getDataTable(list);
    }

    /**
     * 导出审核流程定义列表
     */
    @PreAuthorize("@ss.hasPermi('flowdefine:bpmninfo:export')")
    @Log(title = "审核流程定义", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusBpmnInfo busBpmnInfo)
    {
        List<BusBpmnInfo> list = busBpmnInfoService.selectBusBpmnInfoList(busBpmnInfo);
        ExcelUtil<BusBpmnInfo> util = new ExcelUtil<BusBpmnInfo>(BusBpmnInfo.class);
        util.exportExcel(response, list, "审核流程定义数据");
    }

    /**
     * 获取审核流程定义详细信息
     */
    @PreAuthorize("@ss.hasPermi('flowdefine:bpmninfo:resources')")
    @GetMapping("/{id}/{type}")
    public void resources(@PathVariable Long id,@PathVariable String type) throws IOException {
        InputStream inputStream = busBpmnInfoService.resources(id,type);
        IOUtils.copy(inputStream,ServletUtils.getResponse().getOutputStream());
    }

    /**
     * 新增审核流程定义
     */
    @PreAuthorize("@ss.hasPermi('flowdefine:bpmninfo:deploy')")
    @Log(title = "审核流程定义", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult deploy(MultipartFile file, @Valid BusBpmnInfoVO busBpmnInfoVO)
    {
        return toAjax(busBpmnInfoService.deploy(file,busBpmnInfoVO));
    }

    /**
     * 修改审核流程定义
     */
    @PreAuthorize("@ss.hasPermi('flowdefine:bpmninfo:edit')")
    @Log(title = "审核流程定义", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusBpmnInfo busBpmnInfo)
    {
        return toAjax(busBpmnInfoService.updateBusBpmnInfo(busBpmnInfo));
    }

    /**
     * 删除审核流程定义
     */
    @PreAuthorize("@ss.hasPermi('flowdefine:bpmninfo:cancel')")
    @Log(title = "审核流程定义", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult cancel(@PathVariable Long id)
    {
        return toAjax(busBpmnInfoService.cancel(id));
    }
}
