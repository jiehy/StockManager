package cn.wolfcode.business.appointment.domain.vo;

import cn.wolfcode.business.appointment.util.CheckAnnotations;

/**
 * 结算单对象 bus_statement
 * 
 * @author wolfcode
 * @date 2023-05-31
 */
public class BusStatementVO
{
    public static final Integer STATUS_NOT_PAY = 0;

    public static final Integer STATUS_HAVE_PAY = 1;

    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 客户姓名 */
    @CheckAnnotations(message = "客户姓名不能为空")
    private String customerName;


    /** 客户联系方式 */
    @CheckAnnotations(type = CheckAnnotations.CheckVerifyEnum.PHONE,message = "联系方式不能为空")
    private String customerPhone;

    /** 车牌号码 */
    @CheckAnnotations(type = CheckAnnotations.CheckVerifyEnum.LICENSEPLATE,message = "车牌号码不能为空")
    private String licensePlate;

    /** 汽车类型 */
    @CheckAnnotations(message = "汽车类型不能为空")
    private String carSeries;

    /** 服务类型【0维修/1保养】 */
    @CheckAnnotations(message = "服务类型不能为空")
    private Integer serviceType;

    /** 备注信息 */
    private String info;

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

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getCarSeries() {
        return carSeries;
    }

    public void setCarSeries(String carSeries) {
        this.carSeries = carSeries;
    }

    public Integer getServiceType() {
        return serviceType;
    }

    public void setServiceType(Integer serviceType) {
        this.serviceType = serviceType;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}