package cn.wolfcode.business.appointment.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.wolfcode.business.appointment.domain.vo.BusAppointmentVO;
import cn.wolfcode.common.enums.BusinessType;
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
import cn.wolfcode.business.appointment.domain.BusAppointment;
import cn.wolfcode.business.appointment.service.IBusAppointmentService;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.common.core.page.TableDataInfo;

/**
 * 养修信息预约Controller
 * 
 * @author ruoyi
 * @date 2023-05-28
 */
@RestController
@RequestMapping("/appointment/appointment")
public class BusAppointmentController extends BaseController
{
    @Autowired
    private IBusAppointmentService busAppointmentService;

    /**
     * 查询养修信息预约列表
     */
    @PreAuthorize("@ss.hasPermi('appointment:appointment:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusAppointment busAppointment)
    {
        startPage();
        List<BusAppointment> list = busAppointmentService.selectBusAppointmentList(busAppointment);
        return getDataTable(list);
    }

    /**
     * 导出养修信息预约列表
     */
    @PreAuthorize("@ss.hasPermi('appointment:appointment:export')")
    @Log(title = "养修信息预约", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusAppointment busAppointment)
    {
        List<BusAppointment> list = busAppointmentService.selectBusAppointmentList(busAppointment);
        ExcelUtil<BusAppointment> util = new ExcelUtil<BusAppointment>(BusAppointment.class);
        util.exportExcel(response, list, "养修信息预约数据");
    }

    /**
     * 获取养修信息预约详细信息
     */
    @PreAuthorize("@ss.hasPermi('appointment:appointment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busAppointmentService.selectBusAppointmentById(id));
    }

    /**
     * 新增养修信息预约
     */
    @PreAuthorize("@ss.hasPermi('appointment:appointment:add')")
    @Log(title = "养修信息预约", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusAppointmentVO busAppointmentVo)
    {
        return toAjax(busAppointmentService.insertBusAppointment(busAppointmentVo));
    }

    /**
     * 修改养修信息预约
     */
    @PreAuthorize("@ss.hasPermi('appointment:appointment:edit')")
    @Log(title = "养修信息预约", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusAppointmentVO busAppointmentVo)
    {
        return toAjax(busAppointmentService.updateBusAppointment(busAppointmentVo));
    }

    /**
     * 修改养修信息到店
     */
    @PreAuthorize("@ss.hasPermi('appointment:appointment:arrl')")
    @Log(title = "养修信息预约", businessType = BusinessType.UPDATE)
    @GetMapping("/arrl/{id}")
    public AjaxResult arrl(@PathVariable Long id)
    {
        return toAjax(busAppointmentService.arral(id));
    }

    /**
     * 修改养修信息用户取消
     */
    @PreAuthorize("@ss.hasPermi('appointment:appointment:close')")
    @Log(title = "养修信息预约", businessType = BusinessType.UPDATE)
    @GetMapping("/close/{id}")
    public AjaxResult close(@PathVariable Long id)
    {
        return toAjax(busAppointmentService.close(id));
    }

    /**
     * 删除养修信息预约
     */
    @PreAuthorize("@ss.hasPermi('appointment:appointment:remove')")
    @Log(title = "养修信息预约", businessType = BusinessType.DELETE)
	@DeleteMapping("/{id}")
    public AjaxResult remove(@PathVariable Long id)
    {
        return toAjax(busAppointmentService.deleteBusAppointmentById(id));
    }

    /**
     * 删除养修信息预约
     */
    @PreAuthorize("@ss.hasPermi('appointment:appointment:batchDelete')")
    @Log(title = "养修信息预约", businessType = BusinessType.DELETE)
    @DeleteMapping("/batchDelete/{ids}")
    public AjaxResult batchDelete(@PathVariable Long[] ids)
    {
        return toAjax(busAppointmentService.deleteBusAppointmentByIds(ids));
    }

    /**
     * 生成结算单养修信息预约
     */
    @PreAuthorize("@ss.hasPermi('appointment:appointment:getSettlement')")
    @Log(title = "养修信息预约", businessType = BusinessType.OTHER)
    @PutMapping("/settlement/{id}")
    public AjaxResult settlement(@PathVariable Long id)
    {
        return AjaxResult.success(busAppointmentService.settlement(id));
    }

    /**
     * 同步养修信息预约
     */
    @PreAuthorize("@ss.hasPermi('appointment:appointment:sync')")
    @Log(title = "养修信息预约", businessType = BusinessType.OTHER)
    @PostMapping("/sync")
    public AjaxResult sync(@RequestBody BusAppointmentVO busAppointmentVO)
    {
        busAppointmentService.sync(busAppointmentVO);
        return AjaxResult.success();
    }

}
