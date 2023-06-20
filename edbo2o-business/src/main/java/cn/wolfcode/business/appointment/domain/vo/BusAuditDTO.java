package cn.wolfcode.business.appointment.domain.vo;

import cn.wolfcode.business.appointment.domain.BusServiceItem;
import cn.wolfcode.common.core.domain.entity.SysUser;

import java.math.BigDecimal;
import java.util.List;

public class BusAuditDTO {
    private Long id;
    private Long shopOwnerId;
    private Long financeId;
    private String info;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getShopOwnerId() {
        return shopOwnerId;
    }

    public void setShopOwnerId(Long shopOwnerId) {
        this.shopOwnerId = shopOwnerId;
    }

    public Long getFinanceId() {
        return financeId;
    }

    public void setFinanceId(Long financeId) {
        this.financeId = financeId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
