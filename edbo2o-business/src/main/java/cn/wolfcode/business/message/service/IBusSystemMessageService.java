package cn.wolfcode.business.message.service;

import java.util.List;
import cn.wolfcode.business.message.domain.BusSystemMessage;

/**
 * 系统消息Service接口
 * 
 * @author wolfcode
 * @date 2023-06-10
 */
public interface IBusSystemMessageService 
{
    /**
     * 查询系统消息
     * 
     * @param id 系统消息主键
     * @return 系统消息
     */
    public BusSystemMessage selectBusSystemMessageById(Long id);

    /**
     * 查询系统消息列表
     * 
     * @param busSystemMessage 系统消息
     * @return 系统消息集合
     */
    public List<BusSystemMessage> selectBusSystemMessageList(BusSystemMessage busSystemMessage);

    /**
     * 新增系统消息
     * 
     * @param busSystemMessage 系统消息
     * @return 结果
     */
    public int insertBusSystemMessage(BusSystemMessage busSystemMessage);

    /**
     * 修改系统消息
     * 
     * @param busSystemMessage 系统消息
     * @return 结果
     */
    public int updateBusSystemMessage(BusSystemMessage busSystemMessage);

    /**
     * 批量删除系统消息
     * 
     * @param ids 需要删除的系统消息主键集合
     * @return 结果
     */
    public int deleteBusSystemMessageByIds(Long[] ids);

    /**
     * 删除系统消息信息
     * 
     * @param id 系统消息主键
     * @return 结果
     */
    public int deleteBusSystemMessageById(Long id);
}
