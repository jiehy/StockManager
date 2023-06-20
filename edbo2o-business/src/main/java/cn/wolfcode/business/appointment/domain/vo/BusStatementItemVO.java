package cn.wolfcode.business.appointment.domain.vo;

import cn.wolfcode.business.appointment.domain.BusStatementItem;
import cn.wolfcode.business.appointment.util.CheckAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BusStatementItemVO {
    private List<BusStatementItem> busStatementItems = new ArrayList<>();

    @CheckAnnotations(message = "优惠价不能为空")
    private BigDecimal discountPrice;

    @CheckAnnotations(message = "非法操作")
    private Long statementId;

    public List<BusStatementItem> getBusStatementItems() {
        return busStatementItems;
    }

    public void setBusStatementItems(List<BusStatementItem> busStatementItems) {
        this.busStatementItems = busStatementItems;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public Long getStatementId() {
        return statementId;
    }

    public void setStatementId(Long statementId) {
        this.statementId = statementId;
    }
}
