package cn.wolfcode.warehouse.mapper;

import cn.wolfcode.warehouse.domain.InboundOutboundManagement;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 出入库管理Mapper接口
 * 
 * @author wolfcode
 * @date 2023-06-17
 */
public interface InboundOutboundManagementMapper 
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
     * 删除出入库管理
     * 
     * @param id 出入库管理主键
     * @return 结果
     */
    public int deleteInboundOutboundManagementById(Long id);

    /**
     * 批量删除出入库管理
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteInboundOutboundManagementByIds(Long[] ids);

    int changeStatus(@Param("status") Long status, @Param("id") Long id);
}
