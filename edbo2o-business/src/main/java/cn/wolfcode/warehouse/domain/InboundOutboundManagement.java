package cn.wolfcode.warehouse.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;

/**
 * 出入库管理对象 inbound_outbound_management
 * 
 * @author wolfcode
 * @date 2023-06-17
 */
public class InboundOutboundManagement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 仓库 */
    @Excel(name = "仓库")
    private String warehouse;

    /** 类型 */
    @Excel(name = "类型")
    private Integer type;

    /** 出入库时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出入库时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date entryExitTime;

    /** 录入时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "录入时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date operationTime;

    /** 录入人 */
    @Excel(name = "录入人")
    private String operator;

    /** 备注 */
    @Excel(name = "备注")
    private String info;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** 总数量 */
    @Excel(name = "总数量")
    private Long total;

    /** 总金额 */
    @Excel(name = "总金额")
    private BigDecimal totalAmount;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setWarehouse(String warehouse) 
    {
        this.warehouse = warehouse;
    }

    public String getWarehouse() 
    {
        return warehouse;
    }
    public void setType(Integer type) 
    {
        this.type = type;
    }

    public Integer getType() 
    {
        return type;
    }
    public void setEntryExitTime(Date entryExitTime) 
    {
        this.entryExitTime = entryExitTime;
    }

    public Date getEntryExitTime() 
    {
        return entryExitTime;
    }
    public void setOperationTime(Date operationTime) 
    {
        this.operationTime = operationTime;
    }

    public Date getOperationTime() 
    {
        return operationTime;
    }
    public void setOperator(String operator) 
    {
        this.operator = operator;
    }

    public String getOperator() 
    {
        return operator;
    }
    public void setInfo(String info) 
    {
        this.info = info;
    }

    public String getInfo() 
    {
        return info;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setTotal(Long total) 
    {
        this.total = total;
    }

    public Long getTotal() 
    {
        return total;
    }
    public void setTotalAmount(BigDecimal totalAmount) 
    {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount() 
    {
        return totalAmount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("warehouse", getWarehouse())
            .append("type", getType())
            .append("entryExitTime", getEntryExitTime())
            .append("operationTime", getOperationTime())
            .append("operator", getOperator())
            .append("info", getInfo())
            .append("status", getStatus())
            .append("total", getTotal())
            .append("totalAmount", getTotalAmount())
            .toString();
    }
}
