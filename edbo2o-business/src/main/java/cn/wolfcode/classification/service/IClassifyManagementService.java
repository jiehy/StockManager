package cn.wolfcode.classification.service;


import cn.wolfcode.classification.domain.ClassifyManagement;
import cn.wolfcode.classification.domain.vo.ClassifyManagementVO;
import cn.wolfcode.classification.domain.vo.MoveClassifyVO;

import java.util.List;

/**
 * 分类管理Service接口
 * 
 * @author wolfcode
 * @date 2023-06-16
 */
public interface IClassifyManagementService 
{
    /**
     * 查询分类管理
     * 
     * @param id 分类管理主键
     * @return 分类管理
     */
    public ClassifyManagement selectClassifyManagementById(Long id);

    /**
     * 查询分类管理列表
     * 
     * @param classifyManagement 分类管理
     * @return 分类管理集合
     */
    public List<ClassifyManagement> selectClassifyManagementList(ClassifyManagement classifyManagement);

    /**
     * 查询上级分类列表
     *
     * @return 上级分类集合
     */
    public List<ClassifyManagement> selectSuperiorClassifyList();

    /**
     * 新增分类管理
     * 
     * @param classifyManagement 分类管理
     * @return 结果
     */
    public int insertClassifyManagement(ClassifyManagement classifyManagement);

    /**
     * 修改分类管理
     * 
     * @param classifyManagementVO 分类管理
     * @return 结果
     */
    public int updateClassifyManagement(ClassifyManagementVO classifyManagementVO);

    /**
     * 批量删除分类管理
     * 
     * @param ids 需要删除的分类管理主键集合
     * @return 结果
     */
    public int deleteClassifyManagementByIds(Long[] ids);

    /**
     * 删除分类管理信息
     *
     * @param id 分类管理主键
     * @return 结果
     */
    public int deleteClassifyManagementById(Long id);

    int move(MoveClassifyVO moveClassifyVO);

    List<ClassifyManagement> selectAllList();
}
