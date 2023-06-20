package cn.wolfcode.business.archives.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import cn.wolfcode.business.archives.domain.BusCustomerArchives;
import cn.wolfcode.business.archives.domain.vo.BusCustomerArchivesVO;
import cn.wolfcode.business.archives.qo.BusArchivesQueryObject;
import cn.wolfcode.business.archives.service.IBusCustomerArchivesService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.wolfcode.common.annotation.Log;
import cn.wolfcode.common.core.controller.BaseController;
import cn.wolfcode.common.core.domain.AjaxResult;
import cn.wolfcode.common.enums.BusinessType;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.common.core.page.TableDataInfo;

/**
 * 客户档案Controller
 *
 * @author wolfcode
 * @date 2023-06-09
 */
@RestController
@RequestMapping("/archives/customer")
public class BusCustomerArchivesController extends BaseController {
    @Autowired
    private IBusCustomerArchivesService busCustomerFollowUpService;

    /**
     * 查询客户档案列表
     */
    @PreAuthorize("@ss.hasPermi('archives:customer:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusArchivesQueryObject qo) {
        startPage();
        List<BusCustomerArchives> list = busCustomerFollowUpService.selectBusCustomerFollowUpList(qo);
        return getDataTable(list);
    }

    /**
     * 导出客户档案列表
     */
    @PreAuthorize("@ss.hasPermi('archives:customer:export')")
    @Log(title = "客户档案", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusCustomerArchives busCustomerFollowUp) {
        List<BusCustomerArchives> list = busCustomerFollowUpService.selectBusCustomerFollowUpListExport(busCustomerFollowUp);
        ExcelUtil<BusCustomerArchives> util = new ExcelUtil<BusCustomerArchives>(BusCustomerArchives.class);
        util.exportExcel(response, list, "客户档案数据");
    }

    /**
     * 获取客户档案详细信息
     */
    @PreAuthorize("@ss.hasPermi('archives:customer:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(busCustomerFollowUpService.selectBusCustomerFollowUpById(id));
    }

    /**
     * 新增客户档案
     */
    @PreAuthorize("@ss.hasPermi('archives:customer:add')")
    @Log(title = "客户档案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody @Valid BusCustomerArchivesVO busCustomerArchivesVO) {
        return toAjax(busCustomerFollowUpService.insertBusCustomerFollowUp(busCustomerArchivesVO));
    }

    /**
     * 修改客户档案
     */
    @PreAuthorize("@ss.hasPermi('archives:customer:edit')")
    @Log(title = "客户档案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusCustomerArchivesVO busCustomerFollowUpVO) {
        return toAjax(busCustomerFollowUpService.updateBusCustomerFollowUp(busCustomerFollowUpVO));
    }

    /**
     * 录入人列表
     */
    @PreAuthorize("@ss.hasPermi('archives:customer:enter')")
    @Log(title = "客户档案", businessType = BusinessType.OTHER)
    @GetMapping("/enter")
    public AjaxResult enter() {
        return AjaxResult.success(busCustomerFollowUpService.enter());
    }
}
