package cn.wolfcode.business.message.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import cn.wolfcode.common.annotation.Excel;
import cn.wolfcode.common.core.domain.BaseEntity;

/**
 * 系统消息对象 bus_system_message
 * 
 * @author wolfcode
 * @date 2023-06-10
 */
public class BusSystemMessage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 标题 */
    @Excel(name = "标题")
    private String messageTitle;

    /** 类型 */
    @Excel(name = "类型")
    private String messageOrderNumber;

    /** 单号 */
    @Excel(name = "单号")
    private String messageType;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "创建时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date creationTime;

    /** 到期时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "到期时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date expireTime;

    /** 内容 */
    @Excel(name = "内容")
    private String messageContent;

    /** 接收人 */
    @Excel(name = "接收人")
    private String recipient;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setMessageTitle(String messageTitle) 
    {
        this.messageTitle = messageTitle;
    }

    public String getMessageTitle() 
    {
        return messageTitle;
    }
    public void setMessageOrderNumber(String messageOrderNumber) 
    {
        this.messageOrderNumber = messageOrderNumber;
    }

    public String getMessageOrderNumber() 
    {
        return messageOrderNumber;
    }
    public void setMessageType(String messageType) 
    {
        this.messageType = messageType;
    }

    public String getMessageType() 
    {
        return messageType;
    }
    public void setCreationTime(Date creationTime) 
    {
        this.creationTime = creationTime;
    }

    public Date getCreationTime() 
    {
        return creationTime;
    }
    public void setExpireTime(Date expireTime) 
    {
        this.expireTime = expireTime;
    }

    public Date getExpireTime() 
    {
        return expireTime;
    }
    public void setMessageContent(String messageContent) 
    {
        this.messageContent = messageContent;
    }

    public String getMessageContent() 
    {
        return messageContent;
    }
    public void setRecipient(String recipient) 
    {
        this.recipient = recipient;
    }

    public String getRecipient() 
    {
        return recipient;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("messageTitle", getMessageTitle())
            .append("messageOrderNumber", getMessageOrderNumber())
            .append("messageType", getMessageType())
            .append("creationTime", getCreationTime())
            .append("expireTime", getExpireTime())
            .append("messageContent", getMessageContent())
            .append("recipient", getRecipient())
            .toString();
    }
}
