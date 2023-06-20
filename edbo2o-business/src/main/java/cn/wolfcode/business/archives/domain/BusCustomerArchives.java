package cn.wolfcode.business.archives.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;

/**
 * 客户档案对象 bus_customer_follow_up
 *
 * @author wolfcode
 * @date 2023-06-09
 */
public class BusCustomerArchives extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 客户名字
     */
    @Excel(name = "客户名字")
    private String customerName;

    /**
     * 电话
     */
    @Excel(name = "电话")
    private String customerPhone;

    /**
     * 客户会员【0/普通客户,1/会员客户】
     */
    private Integer customerVip;

    /**
     * 性别
     */
    @Excel(name = "性别")
    private Integer gender;

    /**
     * 车辆信息
     */
    @Excel(name = "车辆信息")
    private String carSeries;

    /**
     * 录入人
     */
    @Excel(name = "录入人")
    private String enterUser;

    /**
     * 录入时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "录入时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date enterTime;

    /**
     * 是否有支付记录
     */
    private Integer isPayRecord;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerVip(Integer customerVip) {
        this.customerVip = customerVip;
    }

    public Integer getCustomerVip() {
        return customerVip;
    }

    public void setCarSeries(String carSeries) {
        this.carSeries = carSeries;
    }

    public String getCarSeries() {
        return carSeries;
    }

    public void setEnterUser(String enterUser) {
        this.enterUser = enterUser;
    }

    public String getEnterUser() {
        return enterUser;
    }

    public void setEnterTime(Date enterTime) {
        this.enterTime = enterTime;
    }

    public Date getEnterTime() {
        return enterTime;
    }

    public void setIsPayRecord(Integer isPayRecord) {
        this.isPayRecord = isPayRecord;
    }

    public Integer getIsPayRecord() {
        return isPayRecord;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}
