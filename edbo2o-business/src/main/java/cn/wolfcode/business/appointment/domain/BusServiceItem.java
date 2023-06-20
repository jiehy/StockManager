package cn.wolfcode.business.appointment.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;

/**
 * 服务项对象 bus_service_item
 * 
 * @author ruoyi
 * @date 2023-05-30
 */
public class BusServiceItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 服务项名称 */
    @Excel(name = "服务项名称")
    private String name;

    /** 服务项原价 */
    @Excel(name = "服务项原价")
    private BigDecimal originalPrice;

    /** 服务项折扣价 */
    @Excel(name = "服务项折扣价")
    private BigDecimal discountPrice;

    /** 是否套餐【1是/0否】 */
    @Excel(name = "是否套餐【1是/0否】")
    private Integer carPackage;

    /** 备注信息 */
    @Excel(name = "备注信息")
    private String info;

    /** 服务分类【0维修/1保养/2其他】 */
    @Excel(name = "服务分类【0维修/1保养/2其他】")
    private Integer serviceCatalog;

    /** 审核状态【0初始化/1审核中/2审核通过/3审核拒绝/4无需审核】 */
    @Excel(name = "审核状态【0初始化/1审核中/2审核通过/3审核拒绝/4无需审核】")
    private Integer auditStatus;

    /** 上架状态【1已上架/0未上架】 */
    @Excel(name = "上架状态【1已上架/0未上架】")
    private Integer saleStatus;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setOriginalPrice(BigDecimal originalPrice) 
    {
        this.originalPrice = originalPrice;
    }

    public BigDecimal getOriginalPrice() 
    {
        return originalPrice;
    }
    public void setDiscountPrice(BigDecimal discountPrice) 
    {
        this.discountPrice = discountPrice;
    }

    public BigDecimal getDiscountPrice() 
    {
        return discountPrice;
    }
    public void setCarPackage(Integer carPackage) 
    {
        this.carPackage = carPackage;
    }

    public Integer getCarPackage() 
    {
        return carPackage;
    }
    public void setInfo(String info) 
    {
        this.info = info;
    }

    public String getInfo() 
    {
        return info;
    }
    public void setServiceCatalog(Integer serviceCatalog) 
    {
        this.serviceCatalog = serviceCatalog;
    }

    public Integer getServiceCatalog() 
    {
        return serviceCatalog;
    }
    public void setAuditStatus(Integer auditStatus) 
    {
        this.auditStatus = auditStatus;
    }

    public Integer getAuditStatus() 
    {
        return auditStatus;
    }
    public void setSaleStatus(Integer saleStatus) 
    {
        this.saleStatus = saleStatus;
    }

    public Integer getSaleStatus() 
    {
        return saleStatus;
    }
}
