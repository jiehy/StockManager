package cn.wolfcode.business.appointment.service;

import java.util.List;
import java.util.Map;

import cn.wolfcode.business.appointment.domain.BusServiceItem;
import cn.wolfcode.business.appointment.domain.vo.BusAuditDTO;
import cn.wolfcode.business.appointment.domain.vo.BusAuditVO;
import cn.wolfcode.business.appointment.domain.vo.BusServiceItemVO;

/**
 * 服务项Service接口
 * 
 * @author ruoyi
 * @date 2023-05-30
 */
public interface IBusServiceItemService 
{
    /**
     * 查询服务项
     * 
     * @param id 服务项主键
     * @return 服务项
     */
    public BusServiceItem selectBusServiceItemById(Long id);

    /**
     * 查询服务项列表
     * 
     * @param busServiceItem 服务项
     * @return 服务项集合
     */
    public List<BusServiceItem> selectBusServiceItemList(BusServiceItem busServiceItem);

    /**
     * 新增服务项
     * 
     * @param busServiceItemVO 服务项
     * @return 结果
     */
    public int insertBusServiceItem(BusServiceItemVO busServiceItemVO);

    /**
     * 修改服务项
     * 
     * @param busServiceItemVO 服务项
     * @return 结果
     */
    public int updateBusServiceItem(BusServiceItemVO busServiceItemVO);

    /**
     * 批量删除服务项
     * 
     * @param ids 需要删除的服务项主键集合
     * @return 结果
     */
    public int deleteBusServiceItemByIds(Long[] ids);

    /**
     * 删除服务项信息
     * 
     * @param id 服务项主键
     * @return 结果
     */
    public int deleteBusServiceItemById(Long id);

    int saleOn(Long id);

    int saleOff(Long id);

    BusAuditVO audit(Long id);

    int subAudit(BusAuditDTO dto);
}
