package cn.wolfcode.warehouse.service;


import cn.wolfcode.warehouse.domain.InboundOutboundManagement;

import java.util.List;

/**
 * 出入库管理Service接口
 * 
 * @author wolfcode
 * @date 2023-06-17
 */
public interface IInboundOutboundManagementService 
{
    /**
     * 查询出入库管理
     * 
     * @param id 出入库管理主键
     * @return 出入库管理
     */
    public InboundOutboundManagement selectInboundOutboundManagementById(Long id);

    /**
     * 查询出入库管理列表
     * 
     * @param inboundOutboundManagement 出入库管理
     * @return 出入库管理集合
     */
    public List<InboundOutboundManagement> selectInboundOutboundManagementList(InboundOutboundManagement inboundOutboundManagement);

    /**
     * 新增出入库管理
     * 
     * @param inboundOutboundManagement 出入库管理
     * @return 结果
     */
    public int insertInboundOutboundManagement(InboundOutboundManagement inboundOutboundManagement);

    /**
     * 修改出入库管理
     * 
     * @param inboundOutboundManagement 出入库管理
     * @return 结果
     */
    public int updateInboundOutboundManagement(InboundOutboundManagement inboundOutboundManagement);

    /**
     * 批量删除出入库管理
     * 
     * @param ids 需要删除的出入库管理主键集合
     * @return 结果
     */
    public int deleteInboundOutboundManagementByIds(Long[] ids);

    /**
     * 删除出入库管理信息
     *
     * @param id 出入库管理主键
     * @return 结果
     */
    public int deleteInboundOutboundManagementById(Long id);

    int invalid(Long id);
}
