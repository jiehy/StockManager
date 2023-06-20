package cn.wolfcode.business.flowdefine.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;

/**
 * 套餐审核对象 bus_car_package_audit
 *
 * @author wolfcode
 * @date 2023-06-04
 */
public class BusCarPackageAudit extends BaseEntity {
    private static final long serialVersionUID = 1L;

    public static final Integer STATUS_IN_ROGRESS = 0;//审核中
    public static final Integer STATUS_REJECT = 1;//审核拒绝
    public static final Integer STATUS_PASS = 2;//审核通过
    public static final Integer STATUS_CANCEL = 3;//审核撤销
    /**
     * $column.columnComment
     */
    private Long id;

    /**
     * 服务单项id
     */
    private Long serviceItemId;

    /**
     * 套餐名称
     */
    @Excel(name = "套餐名称")
    private String serviceItemName;

    /**
     * 套餐价格
     */
    @Excel(name = "套餐价格")
    private BigDecimal serviceItemPrice;

    /**
     * 套餐备注
     */
    @Excel(name = "套餐备注")
    private String serviceItemInfo;

    /**
     * 流程实例id
     */
    private String instanceId;

    /**
     * 创建者
     */
    private String creatorId;

    /**
     * 备注
     */
    private String info;

    /**
     * 审核状态
     */
    @Excel(name = "审核状态")
    private Integer status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setServiceItemId(Long serviceItemId) {
        this.serviceItemId = serviceItemId;
    }

    public Long getServiceItemId() {
        return serviceItemId;
    }

    public void setServiceItemName(String serviceItemName) {
        this.serviceItemName = serviceItemName;
    }

    public String getServiceItemName() {
        return serviceItemName;
    }

    public void setServiceItemPrice(BigDecimal serviceItemPrice) {
        this.serviceItemPrice = serviceItemPrice;
    }

    public BigDecimal getServiceItemPrice() {
        return serviceItemPrice;
    }

    public void setServiceItemInfo(String serviceItemInfo) {
        this.serviceItemInfo = serviceItemInfo;
    }

    public String getServiceItemInfo() {
        return serviceItemInfo;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("serviceItemId", getServiceItemId())
                .append("serviceItemName", getServiceItemName())
                .append("serviceItemPrice", getServiceItemPrice())
                .append("serviceItemInfo", getServiceItemInfo())
                .append("instanceId", getInstanceId())
                .append("creatorId", getCreatorId())
                .append("createTime", getCreateTime())
                .append("info", getInfo())
                .append("status", getStatus())
                .toString();
    }
}
