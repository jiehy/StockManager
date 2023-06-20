package cn.wolfcode.classification.domain.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * 封装树结构
 */
public class SuperiorClassifyVO
{
    // 上级分类id
    private Long id;

    // 分类名
    private String classify;

    // 子分类集合
    private List<SuperiorClassifyVO> children = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public List<SuperiorClassifyVO> getChildren() {
        return children;
    }

    public void setChildren(List<SuperiorClassifyVO> children) {
        this.children = children;
    }

    public SuperiorClassifyVO() {
    }

    public SuperiorClassifyVO(Long id, String classify, List<SuperiorClassifyVO> children) {
        this.id = id;
        this.classify = classify;
        this.children = children;
    }
    public SuperiorClassifyVO(Long id, String classify) {
        this(id,classify,new ArrayList<>());
    }
}