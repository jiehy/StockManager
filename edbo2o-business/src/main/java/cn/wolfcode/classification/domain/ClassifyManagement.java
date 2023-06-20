package cn.wolfcode.classification.domain;

import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类管理对象 classify_management
 * 
 * @author wolfcode
 * @date 2023-06-16
 */
public class ClassifyManagement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键自增 */
    private Long id;

    /** 上级分类 */
    @Excel(name = "上级分类")
    private String superiorClassify;

    /** 分类 */
    @Excel(name = "分类")
    private String classify;

    /** 描述 */
    @Excel(name = "描述")
    private String classifyDescribe;

    /** 分类层级 */
    @Excel(name = "分类层级")
    private String classifyHierarchy;

    private List<ClassifyManagement> children = new ArrayList<>();

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSuperiorClassify(String superiorClassify) 
    {
        this.superiorClassify = superiorClassify;
    }

    public String getSuperiorClassify() 
    {
        return superiorClassify;
    }
    public void setClassify(String classify) 
    {
        this.classify = classify;
    }

    public String getClassify() 
    {
        return classify;
    }
    public void setClassifyDescribe(String classifyDescribe) 
    {
        this.classifyDescribe = classifyDescribe;
    }

    public String getClassifyDescribe() 
    {
        return classifyDescribe;
    }
    public void setClassifyHierarchy(String classifyHierarchy) 
    {
        this.classifyHierarchy = classifyHierarchy;
    }

    public String getClassifyHierarchy() 
    {
        return classifyHierarchy;
    }

    public List<ClassifyManagement> getChildren() {
        return children;
    }

    public void setChildren(List<ClassifyManagement> children) {
        this.children = children;
    }

    public ClassifyManagement() {
    }

    public ClassifyManagement(Long id, String superiorClassify, String classify, String classifyDescribe, String classifyHierarchy) {
        this.id = id;
        this.superiorClassify = superiorClassify;
        this.classify = classify;
        this.classifyDescribe = classifyDescribe;
        this.classifyHierarchy = classifyHierarchy;
    }

    public ClassifyManagement(Long id, String classify, String classifyHierarchy, List<ClassifyManagement> children) {
        this.id = id;
        this.classify = classify;
        this.classifyHierarchy = classifyHierarchy;
        this.children = children;
    }
}
