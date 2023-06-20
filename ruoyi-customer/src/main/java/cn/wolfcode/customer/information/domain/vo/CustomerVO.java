package cn.wolfcode.customer.information.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 客户基本信息管理对象 customer
 *
 * @author HH
 * @date 2023-06-16
 */
public class CustomerVO extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 关键字
     */
    @Excel(name = "关键字")
    private String keyword;

    /**
     * 主键
     */
    private Long id;

    /**
     * 公司名称
     */
    @Excel(name = "公司名称")
    private String name;

    /**
     * 法定代表人
     */
    @Excel(name = "法定代表人")
    private String legalRepresentative;

    /**
     * 成立日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "成立日期", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date dateOfEstablishment;

    /**
     * 经营状态
     */
    @Excel(name = "经营状态")
    private Long operatingStatus;

    /**
     * 注册资金
     */
    @Excel(name = "注册资金")
    private Long registeredCapital;

    /**
     * 所属行业
     */
    @Excel(name = "所属行业")
    private String industry;

    /**
     * 所属省份
     */
    @Excel(name = "所属省份")
    private Long province;

    /**
     * 注册地址
     */
    @Excel(name = "注册地址")
    private String address;

    /**
     * 经营范围
     */
    @Excel(name = "经营范围")
    private String businessScope;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String legalRepresentative) {
        this.legalRepresentative = legalRepresentative;
    }

    public Date getDateOfEstablishment() {
        return dateOfEstablishment;
    }

    public void setDateOfEstablishment(Date dateOfEstablishment) {
        this.dateOfEstablishment = dateOfEstablishment;
    }

    public Long getOperatingStatus() {
        return operatingStatus;
    }

    public void setOperatingStatus(Long operatingStatus) {
        this.operatingStatus = operatingStatus;
    }

    public Long getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(Long registeredCapital) {
        this.registeredCapital = registeredCapital;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public Long getProvince() {
        return province;
    }

    public void setProvince(Long province) {
        this.province = province;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBusinessScope() {
        return businessScope;
    }

    public void setBusinessScope(String businessScope) {
        this.businessScope = businessScope;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("name", getName())
                .append("legalRepresentative", getLegalRepresentative())
                .append("dateOfEstablishment", getDateOfEstablishment())
                .append("operatingStatus", getOperatingStatus())
                .append("registeredCapital", getRegisteredCapital())
                .append("industry", getIndustry())
                .append("province", getProvince())
                .append("address", getAddress())
                .append("businessScope", getBusinessScope())
                .toString();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
