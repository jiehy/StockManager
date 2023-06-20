package cn.wolfcode.business.appointment.domain.vo;

import cn.wolfcode.business.appointment.domain.BusServiceItem;
import cn.wolfcode.common.core.domain.entity.SysUser;

import java.math.BigDecimal;
import java.util.List;

public class BusAuditVO {
    private BusServiceItem serviceItem;
    private List<SysUser> shopOwner;
    private List<SysUser> finances;
    private BigDecimal limit = new BigDecimal("3000");

    public BusServiceItem getServiceItem() {
        return serviceItem;
    }

    public void setServiceItem(BusServiceItem serviceItem) {
        this.serviceItem = serviceItem;
    }

    public List<SysUser> getShopOwner() {
        return shopOwner;
    }

    public void setShopOwner(List<SysUser> shopOwner) {
        this.shopOwner = shopOwner;
    }

    public List<SysUser> getFinances() {
        return finances;
    }

    public void setFinances(List<SysUser> finances) {
        this.finances = finances;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public void setLimit(BigDecimal limit) {
        this.limit = limit;
    }
}
