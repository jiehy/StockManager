package cn.wolfcode.business.flowdefine.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class BusHistoryAuditVO {
    private String serviceName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date engTime;

    private String timeConsuming;

    private String opinion;

    public BusHistoryAuditVO() {
    }

    public BusHistoryAuditVO(String serviceName, Date beginTime, Date engTime, String timeConsuming, String opinion) {
        this.serviceName = serviceName;
        this.beginTime = beginTime;
        this.engTime = engTime;
        this.timeConsuming = timeConsuming;
        this.opinion = opinion;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEngTime() {
        return engTime;
    }

    public void setEngTime(Date engTime) {
        this.engTime = engTime;
    }

    public String getTimeConsuming() {
        return timeConsuming;
    }

    public void setTimeConsuming(String timeConsuming) {
        this.timeConsuming = timeConsuming;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }
}
