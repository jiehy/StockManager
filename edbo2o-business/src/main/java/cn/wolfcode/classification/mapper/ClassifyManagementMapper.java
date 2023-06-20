package cn.wolfcode.classification.mapper;


import cn.wolfcode.classification.domain.ClassifyManagement;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 分类管理Mapper接口
 * 
 * @author wolfcode
 * @date 2023-06-16
 */
public interface ClassifyManagementMapper 
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
     * 新增分类管理
     * 
     * @param classifyManagement 分类管理
     * @return 结果
     */
    public int insertClassifyManagement(ClassifyManagement classifyManagement);

    /**
     * 修改分类管理
     * 
     * @param classifyManagement 分类管理
     * @return 结果
     */
    public int updateClassifyManagement(ClassifyManagement classifyManagement);

    /**
     * 迁移子分类
     *
     * @param id 上级分类id
     * @param classifyHierarchy 层级
     * @return 结果
     */
    public int moveChildClassifyBySuperiorId(@Param("id") Long id, @Param("classifyHierarchy") String classifyHierarchy);

    /**
     * 删除分类管理
     * 
     * @param id 分类管理主键
     * @return 结果
     */
    public int deleteClassifyManagementById(Long id);

    public int deleteClassifyManagementBySuperiorClassify(Long id);

    /**
     * 批量删除分类管理
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteClassifyManagementByIds(Long[] ids);

    /**
     * 查询上级分类列表
     *
     * @return 上级分类集合
     */
    List<ClassifyManagement> selectSuperiorClassifyList();

    /**
     * 连级删除分类
     */
    int deleteClassifyManagementByClassifyHierarchy(String classifyHierarchy);


    List<ClassifyManagement> selectClassifyBySuperiorClassifyId(Long id);

    List<ClassifyManagement> selectAllList();
}
