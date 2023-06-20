package cn.wolfcode.contract.domain;

import java.math.BigDecimal;
import java.util.Date;

import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;

/**
 * 合同管理对象 contract_management
 *
 * @author YL
 * @date 2023-06-16
 */
@Table("contract_management")
public class ContractManagement extends BaseEntity {
    private static final long serialVersionUID = 1L;

    public static Integer AUDIT_FAIL = 0;                       //审核通过
    public static Integer AUDIT_EDIT = 1;                       //待审核
    public static Integer AUDIT_PASS = 2;                       //未审核通过
    public static Integer IS_STAMP = 1;                         //已盖章
    public static Integer NOT_STAMP = 0;                        //未盖章
    public static Integer IS_INVALID = 0;                       //已作废
    public static Integer NOT_IS_INVALID = 1;                   //未作废


    /**
     * 主键
     */
    @Id(value = "id",keyType = KeyType.Auto)
    private Long id;

    /**
     * 客户
     */
    @Excel(name = "客户")
    @Column(value = "affiliate_customers")
    private String affiliateCustomers;

    /**
     * 名称
     */
    @Excel(name = "名称")
    @Column(value = "title")
    private String title;

    /**
     * 编号
     */
    @Excel(name = "编号")
    @Column("no")
    private String no;

    /**
     * 金额
     */
    @Excel(name = "金额")
    @Column("amount")
    private BigDecimal amount;

    /**
     * 开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    @Column("start_date")
    private Date startDate;

    /**
     * 结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    @Column("end_date")
    private Date endDate;

    /**
     * 电子附件
     */
    @Excel(name = "电子附件")
    @Column("electronic_accessories")
    private String electronicAccessories;

    /**
     * 录入人
     */
    @Excel(name = "录入人")
    @Column("entry_person")
    private String entryPerson;

    /**
     * 录入时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "录入时间", width = 30, dateFormat = "yyyy-MM-dd")
    @Column("entry_time")
    private Date entryTime;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    @Column("change_time")
    private Date changeTime;

    /**
     * 审核状态
     */
    @Excel(name = "审核状态【0审核通过/1待审核/2未通过】")
    @Column("audit_status")
    private Integer auditStatus;

    /**
     * 是否盖章
     */
    @Excel(name = "是否盖章【1是/0否】")
    @Column("to_stamp")
    private Integer toStamp;

    /**
     * 是否作废
     */
    @Excel(name = "是否作废【0是/1否】")
    @Column("invalid")
    private Integer invalid;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setAffiliateCustomers(String affiliateCustomers) {
        this.affiliateCustomers = affiliateCustomers;
    }

    public String getAffiliateCustomers() {
        return affiliateCustomers;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getNo() {
        return no;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setElectronicAccessories(String electronicAccessories) {
        this.electronicAccessories = electronicAccessories;
    }

    public String getElectronicAccessories() {
        return electronicAccessories;
    }

    public void setEntryPerson(String entryPerson) {
        this.entryPerson = entryPerson;
    }

    public String getEntryPerson() {
        return entryPerson;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setToStamp(Integer toStamp) {
        this.toStamp = toStamp;
    }

    public Integer getToStamp() {
        return toStamp;
    }

    public void setInvalid(Integer invalid) {
        this.invalid = invalid;
    }

    public Integer getInvalid() {
        return invalid;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("affiliateCustomers", getAffiliateCustomers())
                .append("title", getTitle())
                .append("no", getNo())
                .append("amount", getAmount())
                .append("startDate", getStartDate())
                .append("endDate", getEndDate())
                .append("electronicAccessories", getElectronicAccessories())
                .append("entryPerson", getEntryPerson())
                .append("entryTime", getEntryTime())
                .append("changeTime", getChangeTime())
                .append("auditStatus", getAuditStatus())
                .append("toStamp", getToStamp())
                .append("invalid", getInvalid())
                .toString();
    }
}
