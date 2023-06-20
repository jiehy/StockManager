package cn.wolfcode.business.message.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.wolfcode.business.message.config.WebSocketService;
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
import cn.wolfcode.business.message.domain.BusSystemMessage;
import cn.wolfcode.business.message.service.IBusSystemMessageService;
import cn.wolfcode.common.utils.poi.ExcelUtil;
import cn.wolfcode.common.core.page.TableDataInfo;

/**
 * 系统消息Controller
 * 
 * @author wolfcode
 * @date 2023-06-10
 */
@RestController
@RequestMapping("/system/message")
public class BusSystemMessageController extends BaseController
{
    @Autowired
    private IBusSystemMessageService busSystemMessageService;

    /**
     * 查询系统消息列表
     */
    @PreAuthorize("@ss.hasPermi('system:message:list')")
    @GetMapping("/list")
    public TableDataInfo list(BusSystemMessage busSystemMessage)
    {
        startPage();
        List<BusSystemMessage> list = busSystemMessageService.selectBusSystemMessageList(busSystemMessage);
        return getDataTable(list);
    }

    /**
     * 导出系统消息列表
     */
    @PreAuthorize("@ss.hasPermi('system:message:export')")
    @Log(title = "系统消息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BusSystemMessage busSystemMessage)
    {
        List<BusSystemMessage> list = busSystemMessageService.selectBusSystemMessageList(busSystemMessage);
        ExcelUtil<BusSystemMessage> util = new ExcelUtil<BusSystemMessage>(BusSystemMessage.class);
        util.exportExcel(response, list, "系统消息数据");
    }

    /**
     * 获取系统消息详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:message:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(busSystemMessageService.selectBusSystemMessageById(id));
    }

    /**
     * 新增系统消息
     */
    @PreAuthorize("@ss.hasPermi('system:message:add')")
    @Log(title = "系统消息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BusSystemMessage busSystemMessage)
    {
        return toAjax(busSystemMessageService.insertBusSystemMessage(busSystemMessage));
    }

    /**
     * 修改系统消息
     */
    @PreAuthorize("@ss.hasPermi('system:message:edit')")
    @Log(title = "系统消息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BusSystemMessage busSystemMessage)
    {
        return toAjax(busSystemMessageService.updateBusSystemMessage(busSystemMessage));
    }

    /**
     * 删除系统消息
     */
    @PreAuthorize("@ss.hasPermi('system:message:remove')")
    @Log(title = "系统消息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(busSystemMessageService.deleteBusSystemMessageByIds(ids));
    }

    @RequestMapping("/push/{toUserId}")
    public AjaxResult pushToWeb(String message, @PathVariable String toUserId) throws IOException, IOException {
        WebSocketService.sendInfo(message,toUserId);
        return AjaxResult.success();
    }
}
