package cn.wolfcode.business.appointment.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class BusAppointmentVO {
    public static final Integer STATUS_APPOINTMENT = 0;
    public static final Integer STATUS_ARRIVE = 1;
    public static final Integer STATUS_CANCEL = 2;
    public static final Integer STATUS_TIMEOUT = 3;
    public static final Integer STATUS_SETTLEMENT = 4;
    public static final Integer STATUS_PAY = 5;

    /** $column.columnComment */
    private Long id;

    /** 客户姓名 */
    private String customerName;

    /** 联系方式 */
    private String customerPhone;

    /** 预约时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date appointmentTime;

    /** 车牌号码 */
    private String licensePlate;

    /** 汽车类型 */
    private String carSeries;

    /** 服务类型【维修0/保养1】 */
    private Integer serviceType;

    /** 备注信息 */
    private String info;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getCustomerName()
    {
        return customerName;
    }
    public void setCustomerPhone(String customerPhone)
    {
        this.customerPhone = customerPhone;
    }

    public String getCustomerPhone()
    {
        return customerPhone;
    }
    public void setAppointmentTime(Date appointmentTime)
    {
        this.appointmentTime = appointmentTime;
    }

    public Date getAppointmentTime()
    {
        return appointmentTime;
    }

    public void setLicensePlate(String licensePlate)
    {
        this.licensePlate = licensePlate;
    }

    public String getLicensePlate()
    {
        return licensePlate;
    }
    public void setCarSeries(String carSeries)
    {
        this.carSeries = carSeries;
    }

    public String getCarSeries()
    {
        return carSeries;
    }
    public void setServiceType(Integer serviceType)
    {
        this.serviceType = serviceType;
    }

    public Integer getServiceType()
    {
        return serviceType;
    }
    public void setInfo(String info)
    {
        this.info = info;
    }

    public String getInfo()
    {
        return info;
    }
}
