package cn.wolfcode.contract.domain.dto;

import cn.wolfcode.business.appointment.util.CheckAnnotations;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 每个请求对象对应的参数VO类
 *
 * @author YL
 * @since 2023-06-16 18:58:14
 */
public class ContractManagementReqDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;
    /**
     * 所属企业客户
     */
    @CheckAnnotations(type = CheckAnnotations.CheckVerifyEnum.NULL, message = "客户不能为空")
    private String affiliateCustomers;
    /**
     * 名称
     */
    @CheckAnnotations(type = CheckAnnotations.CheckVerifyEnum.NULL, message = "合同名称不能为空")
    private String title;
    /**
     * 编号
     */
    @CheckAnnotations(type = CheckAnnotations.CheckVerifyEnum.NULL, message = "合同编号不能为空")
    private String no;
    /**
     * 金额
     */
    @CheckAnnotations(type = CheckAnnotations.CheckVerifyEnum.NULL, message = "合同金额不能为空")
    private BigDecimal amount;
    /**
     * 开始日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @CheckAnnotations(type = CheckAnnotations.CheckVerifyEnum.NULL, message = "开始日期不能为空")
    private Date startDate;
    /**
     * 结束日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @CheckAnnotations(type = CheckAnnotations.CheckVerifyEnum.NULL, message = "结束日期不能为空")
    private Date endDate;

    @CheckAnnotations
    private MultipartFile electronicAccessories;

    public Long getId() {
        return id;
    }

    public void setId(String id) {
        if ("null".equals(id)) {
            this.id = null;
            return;
        }
        this.id = Long.valueOf(id);
    }

    public String getAffiliateCustomers() {
        return affiliateCustomers;
    }

    public void setAffiliateCustomers(String affiliateCustomers) {
        this.affiliateCustomers = affiliateCustomers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public MultipartFile getElectronicAccessories() {
        return electronicAccessories;
    }

    public void setElectronicAccessories(MultipartFile electronicAccessories) {
        this.electronicAccessories = electronicAccessories;
    }
}

