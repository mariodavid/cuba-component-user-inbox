package de.diedavids.cuba.userinbox.entity;

import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.Listeners;
import com.haulmont.cuba.core.entity.annotation.Lookup;
import com.haulmont.cuba.core.entity.annotation.LookupType;
import com.haulmont.cuba.security.entity.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Listeners("ddcui_MessageEntityListener")
@Table(name = "DDCUI_MESSAGE")
@Entity(name = "ddcui$Message")
public class Message extends StandardEntity {
    private static final long serialVersionUID = -631256156050655713L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SENDER_ID")
    protected User sender;

    @Lookup(type = LookupType.DROPDOWN, actions = {"lookup", "clear"})
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "RECEIVER_ID")
    protected User receiver;

    @Column(name = "SUBJECT")
    protected String subject;

    @Column(name = "ENTITY_REFERENCE_ID")
    protected String entityReferenceId;

    @Column(name = "ENTITY_REFERENCE_CLASS")
    protected String entityReferenceClass;

    @Column(name = "ENTITY_CAPTION")
    protected String entityCaption;

    @Lob
    @Column(name = "TEXT")
    protected String text;

    @NotNull
    @Column(name = "READ_", nullable = false)
    protected Boolean read = false;

    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    @Column(name = "RECEIVED_AT", nullable = false)
    protected Date receivedAt;

    public void setReceivedAt(Date receivedAt) {
        this.receivedAt = receivedAt;
    }

    public Date getReceivedAt() {
        return receivedAt;
    }


    public void setEntityCaption(String entityCaption) {
        this.entityCaption = entityCaption;
    }

    public String getEntityCaption() {
        return entityCaption;
    }


    public void setRead(Boolean read) {
        this.read = read;
    }

    public Boolean getRead() {
        return read;
    }


    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setEntityReferenceId(String entityReferenceId) {
        this.entityReferenceId = entityReferenceId;
    }

    public String getEntityReferenceId() {
        return entityReferenceId;
    }

    public void setEntityReferenceClass(String entityReferenceClass) {
        this.entityReferenceClass = entityReferenceClass;
    }

    public String getEntityReferenceClass() {
        return entityReferenceClass;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }


    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getSender() {
        return sender;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public User getReceiver() {
        return receiver;
    }


}