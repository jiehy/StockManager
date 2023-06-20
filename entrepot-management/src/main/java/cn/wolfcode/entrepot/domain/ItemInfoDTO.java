package cn.wolfcode.entrepot.domain;

import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 物品基本信息对象 item_info
 * 
 * @author ruoyi
 * @date 2023-06-16
 */
public class ItemInfoDTO extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 所属分类 */
    @Excel(name = "所属分类")
    private String category;

    /** 封面图片 */
    @Excel(name = "封面图片")
    private String coverImage;

    /** 名称 */
    @Excel(name = "名称")
    private String name;

    /** 品牌 */
    @Excel(name = "品牌")
    private String brand;

    public String getSpecification() {
        return specification;
    }

    /** 规格 */
    @Excel(name = "规格")
    private String specification;

    public String getDepict() {
        return depict;
    }

    public void setDepict(String depict) {
        this.depict = depict;
    }

    /** 描述 */
    @Excel(name = "描述")
    private String depict;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCategory(String category) 
    {
        this.category = category;
    }

    public String getCategory() 
    {
        return category;
    }
    public void setCoverImage(String coverImage) 
    {
        this.coverImage = coverImage;
    }

    public String getCoverImage() 
    {
        return coverImage;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setBrand(String brand) 
    {
        this.brand = brand;
    }

    public String getBrand() 
    {
        return brand;
    }
    public void setSpecification(String specification) 
    {
        this.specification = specification;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("category", getCategory())
            .append("coverImage", getCoverImage())
            .append("name", getName())
            .append("brand", getBrand())
            .append("specification", getSpecification())
            .append("depict", getDepict ())
            .toString();
    }
}
