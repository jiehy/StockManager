package cn.wolfcode.warehouse.service.impl;

import cn.wolfcode.warehouse.domain.InboundOutboundManagement;
import cn.wolfcode.warehouse.mapper.InboundOutboundManagementMapper;
import cn.wolfcode.warehouse.service.IInboundOutboundManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 出入库管理Service业务层处理
 * 
 * @author wolfcode
 * @date 2023-06-17
 */
@Service
public class InboundOutboundManagementServiceImpl implements IInboundOutboundManagementService
{
    @Autowired
    private InboundOutboundManagementMapper inboundOutboundManagementMapper;

    /**
     * 查询出入库管理
     * 
     * @param id 出入库管理主键
     * @return 出入库管理
     */
    @Override
    public InboundOutboundManagement selectInboundOutboundManagementById(Long id)
    {
        return inboundOutboundManagementMapper.selectInboundOutboundManagementById(id);
    }

    /**
     * 查询出入库管理列表
     * 
     * @param inboundOutboundManagement 出入库管理
     * @return 出入库管理
     */
    @Override
    public List<InboundOutboundManagement> selectInboundOutboundManagementList(InboundOutboundManagement inboundOutboundManagement)
    {
        return inboundOutboundManagementMapper.selectInboundOutboundManagementList(inboundOutboundManagement);
    }

    /**
     * 新增出入库管理
     * 
     * @param inboundOutboundManagement 出入库管理
     * @return 结果
     */
    @Override
    public int insertInboundOutboundManagement(InboundOutboundManagement inboundOutboundManagement)
    {
        return inboundOutboundManagementMapper.insertInboundOutboundManagement(inboundOutboundManagement);
    }

    /**
     * 修改出入库管理
     * 
     * @param inboundOutboundManagement 出入库管理
     * @return 结果
     */
    @Override
    public int updateInboundOutboundManagement(InboundOutboundManagement inboundOutboundManagement)
    {
        return inboundOutboundManagementMapper.updateInboundOutboundManagement(inboundOutboundManagement);
    }

    /**
     * 批量删除出入库管理
     * 
     * @param ids 需要删除的出入库管理主键
     * @return 结果
     */
    @Override
    public int deleteInboundOutboundManagementByIds(Long[] ids)
    {
        return inboundOutboundManagementMapper.deleteInboundOutboundManagementByIds(ids);
    }

    /**
     * 删除出入库管理信息
     *
     * @param id 出入库管理主键
     * @return 结果
     */
    @Override
    public int deleteInboundOutboundManagementById(Long id) {
        return inboundOutboundManagementMapper.deleteInboundOutboundManagementById(id);
    }

    @Override
    public int invalid(Long id) {
        return inboundOutboundManagementMapper.changeStatus(0L, id);
    }
}
