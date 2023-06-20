package cn.wolfcode.business.appointment.domain.vo;

import java.math.BigDecimal;

/**
 * 服务项对象 bus_service_item
 * 
 * @author ruoyi
 * @date 2023-05-30
 */
public class BusServiceItemVO
{
    public static final Integer CAR_PACKAGE_NO = 0;
    public static final Integer CAR_PACKAGE_YES = 1;
    public static final Integer AUDIT_STATUS_INITIALIZE = 0;
    public static final Integer AUDIT_STATUS_REVIEW = 1;
    public static final Integer AUDIT_STATUS_APPROVED = 2;
    public static final Integer AUDIT_STATUS_REFUSED = 3;
    public static final Integer AUDIT_STATUS_NOT_REVIEW = 4;
    public static final Integer SALE_STATUS_OFF = 0;
    public static final Integer SALE_STATUS_ON = 1;

    /** $column.columnComment */
    private Long id;

    /** 服务项名称 */
    private String name;

    /** 服务项原价 */
    private BigDecimal originalPrice;

    /** 服务项折扣价 */
    private BigDecimal discountPrice;

    /** 是否套餐【1是/0否】 */
    private Integer carPackage;

    /** 备注信息 */
    private String info;

    /** 服务分类【0维修/1保养/2其他】 */
    private Integer serviceCatalog;

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
}
