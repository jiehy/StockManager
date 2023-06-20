package cn.wolfcode.business.message.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.wolfcode.business.message.mapper.BusSystemMessageMapper;
import cn.wolfcode.business.message.domain.BusSystemMessage;
import cn.wolfcode.business.message.service.IBusSystemMessageService;

/**
 * 系统消息Service业务层处理
 * 
 * @author wolfcode
 * @date 2023-06-10
 */
@Service
public class BusSystemMessageServiceImpl implements IBusSystemMessageService 
{
    @Autowired
    private BusSystemMessageMapper busSystemMessageMapper;

    /**
     * 查询系统消息
     * 
     * @param id 系统消息主键
     * @return 系统消息
     */
    @Override
    public BusSystemMessage selectBusSystemMessageById(Long id)
    {
        return busSystemMessageMapper.selectBusSystemMessageById(id);
    }

    /**
     * 查询系统消息列表
     * 
     * @param busSystemMessage 系统消息
     * @return 系统消息
     */
    @Override
    public List<BusSystemMessage> selectBusSystemMessageList(BusSystemMessage busSystemMessage)
    {
        return busSystemMessageMapper.selectBusSystemMessageList(busSystemMessage);
    }

    /**
     * 新增系统消息
     * 
     * @param busSystemMessage 系统消息
     * @return 结果
     */
    @Override
    public int insertBusSystemMessage(BusSystemMessage busSystemMessage)
    {
        return busSystemMessageMapper.insertBusSystemMessage(busSystemMessage);
    }

    /**
     * 修改系统消息
     * 
     * @param busSystemMessage 系统消息
     * @return 结果
     */
    @Override
    public int updateBusSystemMessage(BusSystemMessage busSystemMessage)
    {
        return busSystemMessageMapper.updateBusSystemMessage(busSystemMessage);
    }

    /**
     * 批量删除系统消息
     * 
     * @param ids 需要删除的系统消息主键
     * @return 结果
     */
    @Override
    public int deleteBusSystemMessageByIds(Long[] ids)
    {
        return busSystemMessageMapper.deleteBusSystemMessageByIds(ids);
    }

    /**
     * 删除系统消息信息
     * 
     * @param id 系统消息主键
     * @return 结果
     */
    @Override
    public int deleteBusSystemMessageById(Long id)
    {
        return busSystemMessageMapper.deleteBusSystemMessageById(id);
    }
}
