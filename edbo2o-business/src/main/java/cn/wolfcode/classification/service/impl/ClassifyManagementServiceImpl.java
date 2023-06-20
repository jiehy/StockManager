package cn.wolfcode.classification.service.impl;

import cn.wolfcode.classification.domain.ClassifyManagement;
import cn.wolfcode.classification.domain.vo.ClassifyManagementVO;
import cn.wolfcode.classification.domain.vo.MoveClassifyVO;
import cn.wolfcode.classification.mapper.ClassifyManagementMapper;
import cn.wolfcode.classification.service.IClassifyManagementService;
import cn.wolfcode.common.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 分类管理Service业务层处理
 * 
 * @author wolfcode
 * @date 2023-06-16
 */
@Service
public class ClassifyManagementServiceImpl implements IClassifyManagementService
{
    @Autowired
    private ClassifyManagementMapper classifyManagementMapper;

    /**
     * 查询分类管理
     * 
     * @param id 分类管理主键
     * @return 分类管理
     */
    @Override
    public ClassifyManagement selectClassifyManagementById(Long id)
    {
        return classifyManagementMapper.selectClassifyManagementById(id);
    }

    /**
     * 查询分类管理列表
     * 
     * @param classifyManagement 分类管理
     * @return 分类管理
     */
    @Override
    public List<ClassifyManagement> selectClassifyManagementList(ClassifyManagement classifyManagement)
    {
        return classifyManagementMapper.selectClassifyManagementList(classifyManagement);
    }

    @Override
    public List<ClassifyManagement> selectSuperiorClassifyList() {
        List<ClassifyManagement> list = classifyManagementMapper.selectSuperiorClassifyList();
        List<ClassifyManagement> superiorClassify = new ArrayList<>();
        List<ClassifyManagement> childClassify = new ArrayList<>();
        for (ClassifyManagement classify : list) {
            if(classify.getId().toString().equals(classify.getSuperiorClassify())) {
                superiorClassify.add(new ClassifyManagement(classify.getId(), classify.getClassify(), classify.getClassifyHierarchy(), new ArrayList<>()));
            } else {
                childClassify.add(classify);
            }
        }
        List<ClassifyManagement> subtree = getSubtree(childClassify);
        for (ClassifyManagement superior : superiorClassify) {
            for (ClassifyManagement child : subtree) {
                if(superior.getId().toString().equals(child.getSuperiorClassify())) {
                    superior.getChildren().add(new ClassifyManagement(child.getId(), child.getClassify(), child.getClassifyHierarchy(), child.getChildren()));
                }
            }
        }
        return superiorClassify;
    }

    private static List<ClassifyManagement> getSubtree(List<ClassifyManagement> childClassify) {
        List<ClassifyManagement> list = new ArrayList<>();
        for (ClassifyManagement child : childClassify) {
            recursiveList(childClassify, child);
            list.add(child);
        }
        return list;
    }

    private static void recursiveList(List<ClassifyManagement> childClassify,ClassifyManagement classify) {
        List<ClassifyManagement> childList = getChildList(childClassify, classify);
        classify.setChildren(childList);
        for (ClassifyManagement child : childList) {
            if(getChildList(childClassify,child).size() > 0) {
                recursiveList(childList,child);
            }
        }
    }

    private static List<ClassifyManagement> getChildList(List<ClassifyManagement> childClassify,ClassifyManagement classify) {
        ArrayList<ClassifyManagement> list = new ArrayList<>();
        Iterator<ClassifyManagement> iterator = childClassify.iterator();
        while (iterator.hasNext()) {
            ClassifyManagement next = iterator.next();
            if(next.getSuperiorClassify().equals(classify.getId().toString())) {
                list.add(next);
            }
        }
        return list;
    }

    /**
     * 新增分类管理
     * 
     * @param classifyManagement 分类管理
     */
    @Override
    @Transactional
    public int insertClassifyManagement(ClassifyManagement classifyManagement)
    {
        Assert.notNull(classifyManagement,"非法操作");
        Assert.hasLength(classifyManagement.getClassify(),"分类名称不能为空");

        if(!StringUtils.isEmpty(classifyManagement.getSuperiorClassify())) {
            ClassifyManagement management = selectClassifyManagementById(Long.valueOf(classifyManagement.getSuperiorClassify()));
            String tier = management.getClassifyHierarchy() + "," + management.getId().toString();
            classifyManagement.setClassifyHierarchy(tier);
            return classifyManagementMapper.insertClassifyManagement(classifyManagement);
        }

        classifyManagementMapper.insertClassifyManagement(classifyManagement);
        Long id = classifyManagement.getId();
        return classifyManagementMapper.updateClassifyManagement(new ClassifyManagement(id,id.toString(),null,null,id.toString()));
    }

    /**
     * 修改分类管理
     * 
     * @param classifyManagementVO 分类管理
     */
    @Override
    @Transactional
    public int updateClassifyManagement(ClassifyManagementVO classifyManagementVO)
    {
        Assert.notNull(classifyManagementVO,"非法操作");

        ClassifyManagement classify = classifyManagementMapper.selectClassifyManagementById(classifyManagementVO.getId());
        Assert.notNull(classify,"非法操作");

        ClassifyManagement classifyManagement = new ClassifyManagement();
        BeanUtils.copyProperties(classifyManagementVO,classifyManagement);

        return classifyManagementMapper.updateClassifyManagement(classifyManagement);
    }

    /**
     * 批量删除分类管理
     * 
     * @param ids 需要删除的分类管理主键
     * @return 结果
     */
    @Override
    public int deleteClassifyManagementByIds(Long[] ids)
    {
        return classifyManagementMapper.deleteClassifyManagementByIds(ids);
    }

    /**
     * 删除分类管理信息
     * 
     * @param id 分类管理主键
     * @return 结果
     */
    @Override
    @Transactional
    public int deleteClassifyManagementById(Long id) {
        Assert.notNull(id, "非法操作");
        ClassifyManagement management = classifyManagementMapper.selectClassifyManagementById(id);
        Assert.notNull(management, "非法操作");
        if (!id.toString().equals(management.getSuperiorClassify())) {
            classifyManagementMapper.deleteClassifyManagementById(id);
        }
        return classifyManagementMapper.deleteClassifyManagementByClassifyHierarchy(management.getClassifyHierarchy() + id.toString());
    }

    @Override
    @Transactional
    public int move(MoveClassifyVO moveClassifyVO) {
        Assert.notNull(moveClassifyVO,"非法操作");
        Assert.notNull(moveClassifyVO.getSource(),"被迁移分类不能为空");
        Assert.state(!moveClassifyVO.getSource().equals(moveClassifyVO.getTarget()),"迁移分类不能相同");
        ClassifyManagement source = classifyManagementMapper.selectClassifyManagementById(moveClassifyVO.getSource());
        Assert.notNull(source,"参数异常");
        ClassifyManagement target = classifyManagementMapper.selectClassifyManagementById(moveClassifyVO.getTarget());
        Assert.notNull(target,"参数异常");
        Assert.state(target.getClassifyHierarchy().split(",").length < 3,"不能迁移到最低层级");
        if (moveClassifyVO.isInclude()) {
            // 第一层级迁移
            if(source.getId().toString().equals(source.getSuperiorClassify())) {
                List<ClassifyManagement> list = classifyManagementMapper.selectClassifyBySuperiorClassifyId(source.getId());
                if(list.size() > 0) {
                    Assert.state(target.getClassifyHierarchy().split(",").length < 2,"迁移后层级超过三层");
                }
                for (ClassifyManagement classify : list) {
                    Assert.state(classifyManagementMapper.selectClassifyBySuperiorClassifyId(classify.getId()).size() == 0,"迁移后层级超过三层");
                }
                source.setSuperiorClassify(target.getId().toString());
                source.setClassifyHierarchy(target.getClassifyHierarchy() + "," + target.getId());
                classifyManagementMapper.moveChildClassifyBySuperiorId(source.getId()
                        ,source.getClassifyHierarchy() + "," + source.getId());

                return classifyManagementMapper.updateClassifyManagement(source);
            }

            // 第二层级迁移
            if (source.getClassifyHierarchy().split(",").length == 2) {
                source.setSuperiorClassify(target.getId().toString());
                source.setClassifyHierarchy(target.getClassifyHierarchy() + "," + target.getId());
                classifyManagementMapper.moveChildClassifyBySuperiorId(source.getId()
                        , source.getClassifyHierarchy() + "," + source.getId());
                return classifyManagementMapper.updateClassifyManagement(source);
            }
        }
        return 0;
    }

    @Override
    public List<ClassifyManagement> selectAllList() {
        return classifyManagementMapper.selectAllList();
    }
}
