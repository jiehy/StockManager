package cn.wolfcode.classification.controller;

import cn.wolfcode.classification.domain.ClassifyManagement;
import cn.wolfcode.classification.domain.vo.ClassifyManagementVO;
import cn.wolfcode.classification.domain.vo.MoveClassifyVO;
import cn.wolfcode.classification.service.IClassifyManagementService;
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

/**
 * 分类管理Controller
 * 
 * @author wolfcode
 * @date 2023-06-16
 */
@RestController
@RequestMapping("/classify/management")
public class ClassifyManagementController extends BaseController
{
    @Autowired
    private IClassifyManagementService classifyManagementService;

    /**
     * 查询分类管理列表
     */
    @PreAuthorize("@ss.hasPermi('classify:management:list')")
    @GetMapping("/list")
    public TableDataInfo list(ClassifyManagement classifyManagement) {
        startPage();
        return getDataTable(classifyManagementService.selectClassifyManagementList(classifyManagement));
    }

    /**
     * 查询分类管理列表
     */
    @PreAuthorize("@ss.hasPermi('classify:management:list')")
    @GetMapping("/all")
    public AjaxResult all() {
        return AjaxResult.success(classifyManagementService.selectAllList());
    }

    /**
     * 查询上级分类列表
     */
    @PreAuthorize("@ss.hasPermi('classify:management:list')")
    @GetMapping("/superior")
    public AjaxResult superiorList() {
        return AjaxResult.success(classifyManagementService.selectSuperiorClassifyList());
    }

    /**
     * 导出分类管理列表
     */
    @PreAuthorize("@ss.hasPermi('classify:management:export')")
    @Log(title = "分类管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ClassifyManagement classifyManagement)
    {
        List<ClassifyManagement> list = classifyManagementService.selectClassifyManagementList(classifyManagement);
        ExcelUtil<ClassifyManagement> util = new ExcelUtil<ClassifyManagement>(ClassifyManagement.class);
        util.exportExcel(response, list, "分类管理数据");
    }

    /**
     * 获取分类管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('classify:management:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(classifyManagementService.selectClassifyManagementById(id));
    }

    /**
     * 新增分类管理
     */
    @PreAuthorize("@ss.hasPermi('classify:management:add')")
    @Log(title = "分类管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ClassifyManagement classifyManagement)
    {
        return toAjax(classifyManagementService.insertClassifyManagement(classifyManagement));
    }

    /**
     * 修改分类管理
     */
    @PreAuthorize("@ss.hasPermi('classify:management:edit')")
    @Log(title = "分类管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ClassifyManagementVO classifyManagementVO)
    {
        return toAjax(classifyManagementService.updateClassifyManagement(classifyManagementVO));
    }

    /**
     * 删除分类管理
     */
    @PreAuthorize("@ss.hasPermi('classify:management:remove')")
    @Log(title = "分类管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)
    {
        return toAjax(classifyManagementService.deleteClassifyManagementById(id));
    }

    /**
     * 分类迁移
     */
    @PreAuthorize("@ss.hasPermi('classify:management:migration')")
    @Log(title = "分类迁移", businessType = BusinessType.OTHER)
    @PutMapping("/move")
    public AjaxResult move(@RequestBody MoveClassifyVO moveClassifyVO)
    {
        return toAjax(classifyManagementService.move(moveClassifyVO));
    }
}
