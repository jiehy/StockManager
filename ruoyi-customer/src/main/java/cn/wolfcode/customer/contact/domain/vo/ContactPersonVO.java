package cn.wolfcode.customer.contact.domain.vo;

import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 客户联系人对象 contact_person
 *
 * @author HH
 * @date 2023-06-16
 */
public class ContactPersonVO extends BaseEntity {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private Long id;


    /**
     * 关键字
     */
    private String keyword;
    /**
     * 企业
     */
    @Excel(name = "企业")
    private String enterpriseCustomers;
    /**
     * 联系人名字
     */
    @Excel(name = "联系人名字")
    private String name;
    /**
     * 性别
     */
    @Excel(name = "性别")
    private Integer sex;
    /**
     * 年龄
     */
    @Excel(name = "年龄")
    private Integer age;
    /**
     * 电话
     */
    @Excel(name = "电话")
    private String phone;
    /**
     * 职位
     */
    @Excel(name = "职位")
    private String job;
    /**
     * 部门
     */
    @Excel(name = "部门")
    private String dept;
    /**
     * 任职状态
     */
    @Excel(name = "任职状态")
    private Long employmentStatus;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnterpriseCustomers() {
        return enterpriseCustomers;
    }

    public void setEnterpriseCustomers(String enterpriseCustomers) {
        this.enterpriseCustomers = enterpriseCustomers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Long getEmploymentStatus() {
        return employmentStatus;
    }

    public void setEmploymentStatus(Long employmentStatus) {
        this.employmentStatus = employmentStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("enterpriseCustomers", getEnterpriseCustomers())
                .append("name", getName())
                .append("sex", getSex())
                .append("age", getAge())
                .append("phone", getPhone())
                .append("job", getJob())
                .append("dept", getDept())
                .append("employmentStatus", getEmploymentStatus())
                .toString();
    }
}
