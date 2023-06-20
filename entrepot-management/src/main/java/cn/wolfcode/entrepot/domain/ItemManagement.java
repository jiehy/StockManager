package cn.wolfcode.entrepot.domain;

import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 物品管理对象 item_management
 * 
 * @author ruoyi
 * @date 2023-06-16
 */
public class ItemManagement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 物品基本信息 */
    @Excel(name = "物品基本信息")
    private String info;

    /** 上级分类 */
    @Excel(name = "上级分类")
    private String subHead;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String categoryName;

    /** 分类描述 */
    @Excel(name = "分类描述")
    private String desc;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setInfo(String info) 
    {
        this.info = info;
    }

    public String getInfo() 
    {
        return info;
    }
    public void setSubHead(String subHead) 
    {
        this.subHead = subHead;
    }

    public String getSubHead() 
    {
        return subHead;
    }
    public void setCategoryName(String categoryName) 
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName() 
    {
        return categoryName;
    }
    public void setDesc(String desc) 
    {
        this.desc = desc;
    }

    public String getDesc() 
    {
        return desc;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("info", getInfo())
            .append("subHead", getSubHead())
            .append("categoryName", getCategoryName())
            .append("desc", getDesc())
            .toString();
    }
}
