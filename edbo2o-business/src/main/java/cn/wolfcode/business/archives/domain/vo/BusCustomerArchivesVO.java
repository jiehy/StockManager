package cn.wolfcode.business.archives.domain.vo;

import cn.wolfcode.business.appointment.util.CheckAnnotations;
public class BusCustomerArchivesVO {
    public static final Integer VIP_NO = 0;
    public static final Integer VIP_YES = 1;
    public static final Integer PAY_RECORD_NO = 0;
    public static final Integer PAY_RECORD_HAVE = 1;

    /**
     * 主键
     */
    private Long id;

    /**
     * 客户名字
     */
    @CheckAnnotations(message = "客户名字不能为空")
    private String customerName;

    /**
     * 电话
     */
    @CheckAnnotations(type = CheckAnnotations.CheckVerifyEnum.PHONE, message = "电话不能为空")
    private String customerPhone;

    /**
     * 性别
     */
    private int gender;

    /**
     * 车辆信息
     */
    private String carSeries;

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

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCarSeries() {
        return carSeries;
    }

    public void setCarSeries(String carSeries) {
        this.carSeries = carSeries;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}
