package cn.wolfcode.classification.domain.vo;

public class ClassifyManagementVO
{
    /** 主键自增 */
    private Long id;

    /** 分类 */
    private String classify;

    /** 描述 */
    private String classifyDescribe;

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

    public String getClassifyDescribe() {
        return classifyDescribe;
    }

    public void setClassifyDescribe(String classifyDescribe) {
        this.classifyDescribe = classifyDescribe;
    }
}
