package ru.atott.combiq.dao.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import ru.atott.combiq.dao.Types;

import java.util.Date;
import java.util.List;

@Document(indexName = "#{domainResolver.resolveSiteIndex()}", type = Types.event)
public class EventEntity {

    public static final String CREATE_DATE_FIELD = "createDate";

    @Id
    private String id;

    private Date createDate;

    private String message;

    private String creatorUserId;

    private String creatorUserName;

    private List<Link> relevantLinks;

    private EventType type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatorUserId() {
        return creatorUserId;
    }

    public void setCreatorUserId(String creatorUserId) {
        this.creatorUserId = creatorUserId;
    }

    public String getCreatorUserName() {
        return creatorUserName;
    }

    public void setCreatorUserName(String creatorUserName) {
        this.creatorUserName = creatorUserName;
    }

    public List<Link> getRelevantLinks() {
        return relevantLinks;
    }

    public void setRelevantLinks(List<Link> relevantLinks) {
        this.relevantLinks = relevantLinks;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }
}
