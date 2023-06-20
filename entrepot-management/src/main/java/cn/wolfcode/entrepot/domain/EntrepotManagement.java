package cn.wolfcode.entrepot.domain;


import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 仓库管理对象 entrepot_management
 * 
 * @author cjj
 * @date 2023-06-16
 */
public class EntrepotManagement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 仓库名称 */
    @Excel(name = "仓库名称")
    private String entrepotName;

    /** 地址 */
    @Excel(name = "地址")
    private String entrepotAddress;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    private String keyword;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setEntrepotName(String entrepotName) 
    {
        this.entrepotName = entrepotName;
    }

    public String getEntrepotName() 
    {
        return entrepotName;
    }
    public void setEntrepotAddress(String entrepotAddress) 
    {
        this.entrepotAddress = entrepotAddress;
    }

    public String getEntrepotAddress() 
    {
        return entrepotAddress;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("entrepotName", getEntrepotName())
            .append("entrepotAddress", getEntrepotAddress())
            .toString();
    }
}
