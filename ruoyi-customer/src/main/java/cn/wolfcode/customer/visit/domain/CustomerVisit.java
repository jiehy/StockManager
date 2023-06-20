package cn.wolfcode.customer.visit.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 客户拜访对象 customer_visit
 *
 * @author HH
 * @date 2023-06-16
 */
public class CustomerVisit extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * $column.columnComment
     */
    @Excel(name = "序号")
    private Long id;

    /**
     * 企业名称
     */
    @Excel(name = "企业名称")
    private String customerName;

    /**
     * 关键字
     */
    @Excel(name = "关键字")
    private String keyword;

    /**
     * 联系人名称
     */
    @Excel(name = "联系人名称")
    private String contactPersonName;

    /**
     * 拜访原因
     */
    @Excel(name = "拜访原因")
    private String visitReason;

    /**
     * 拜访方式
     */
    @Excel(name = "拜访方式")
    private Long visitWay;

    /**
     * 拜访时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "拜访时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date visitDate;

    /**
     * 交流情况
     */
    @Excel(name = "交流情况")
    private String communication;
    /**
     * 录入时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "录入时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date entryTime;

    public String getKeyword() {
        return keyword;
    }

    /**
     * 录入人
     */
    @Excel(name = "录入人")
    private String entryPerson;

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    public Long getVisitWay() {
        return visitWay;
    }

    public void setVisitWay(Long visitWay) {
        this.visitWay = visitWay;
    }

    public Date getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }

    public String getCommunication() {
        return communication;
    }

    public void setCommunication(String communication) {
        this.communication = communication;
    }

    public String getEntryPerson() {
        return entryPerson;
    }

    public void setEntryPerson(String entryPerson) {
        this.entryPerson = entryPerson;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("customerName", getCustomerName())
                .append("contactPersonName", getContactPersonName())
                .append("visitReason", getVisitReason())
                .append("visitWay", getVisitWay())
                .append("visitDate", getVisitDate())
                .append("communication", getCommunication())
                .append("entryPerson", getEntryPerson())
                .append("entryTime", getEntryTime())
                .toString();
    }
}
