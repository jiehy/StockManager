package cn.wolfcode.business.appointment.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

import cn.wolfcode.business.appointment.domain.BusServiceItem;
import cn.wolfcode.business.appointment.domain.vo.BusAuditDTO;
import cn.wolfcode.business.appointment.domain.vo.BusServiceItemVO;
import cn.wolfcode.business.appointment.service.IBusServiceItemService;
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
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.common.core.page.TableDataInfo;

/**
 * 服务项Controller
 * 
 * @author ruoyi
 * @date 2023-05-30
 */
@RestController
@RequestMapping("/service/service")
public class BusServiceItemController extends BaseController
{
    @Autowired
    private IBusServiceItemService busServiceItemService;

    /**
     * 查询服务项列表
     */
    @PreAuthorize("@ss.hasPermi('service:service:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusServiceItem busServiceItem)
    {
        startPage();
        List<BusServiceItem> list = busServiceItemService.selectBusServiceItemList(busServiceItem);
        return getDataTable(list);
    }

    /**
     * 导出服务项列表
     */
    @PreAuthorize("@ss.hasPermi('service:service:export')")
    @Log(title = "服务项", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusServiceItem busServiceItem)
    {
        List<BusServiceItem> list = busServiceItemService.selectBusServiceItemList(busServiceItem);
        ExcelUtil<BusServiceItem> util = new ExcelUtil<BusServiceItem>(BusServiceItem.class);
        util.exportExcel(response, list, "服务项数据");
    }

    /**
     * 获取服务项详细信息
     */
    @PreAuthorize("@ss.hasPermi('service:service:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busServiceItemService.selectBusServiceItemById(id));
    }

    /**
     * 新增服务项
     */
    @PreAuthorize("@ss.hasPermi('service:service:add')")
    @Log(title = "服务项", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusServiceItemVO busServiceItemVO)
    {
        return toAjax(busServiceItemService.insertBusServiceItem(busServiceItemVO));
    }

    /**
     * 修改服务项
     */
    @PreAuthorize("@ss.hasPermi('service:service:edit')")
    @Log(title = "服务项", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusServiceItemVO busServiceItemVO)
    {
        return toAjax(busServiceItemService.updateBusServiceItem(busServiceItemVO));
    }

    /**
     * 删除服务项
     */
    @PreAuthorize("@ss.hasPermi('service:service:remove')")
    @Log(title = "服务项", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busServiceItemService.deleteBusServiceItemByIds(ids));
    }

    /**
     * 上架服务项
     */
    @PreAuthorize("@ss.hasPermi('service:service:saleOn')")
    @Log(title = "服务项", businessType = BusinessType.UPDATE)
    @PutMapping("/saleOn/{id}")
    public AjaxResult saleOn(@PathVariable Long id)
    {
        return toAjax(busServiceItemService.saleOn(id));
    }

    /**
     * 下架服务项
     */
    @PreAuthorize("@ss.hasPermi('service:service:saleOff')")
    @Log(title = "服务项", businessType = BusinessType.UPDATE)
    @PutMapping("/saleOff/{id}")
    public AjaxResult saleOff(@PathVariable Long id)
    {
        return toAjax(busServiceItemService.saleOff(id));
    }

    @PreAuthorize("@ss.hasPermi('service:service:audit')")
    @Log(title = "服务项", businessType = BusinessType.OTHER)
    @GetMapping("/audit/{id}")
    public AjaxResult audit(@PathVariable Long id)
    {
        return AjaxResult.success(busServiceItemService.audit(id));
    }

    @PreAuthorize("@ss.hasPermi('service:service:audit')")
    @Log(title = "服务项", businessType = BusinessType.OTHER)
    @PostMapping("/subAudit")
    public AjaxResult audit(@RequestBody BusAuditDTO dto)
    {
        return toAjax(busServiceItemService.subAudit(dto));
    }
}
