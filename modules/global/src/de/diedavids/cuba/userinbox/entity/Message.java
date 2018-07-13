package de.diedavids.cuba.userinbox.entity;

import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.*;
import com.haulmont.cuba.core.global.AppBeans;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.security.entity.User;
import de.diedavids.cuba.entitysoftreference.EntitySoftReferenceConverter;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.haulmont.cuba.core.entity.Entity;

@Listeners("ddcui_MessageEntityListener")
@Table(name = "DDCUI_MESSAGE")
@javax.persistence.Entity(name = "ddcui$Message")
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


    @SystemLevel
    @Convert(converter = EntitySoftReferenceConverter.class)
    @MetaProperty(datatype = "EntitySoftReference")
    @Column(name = "SHAREABLE")
    protected Entity shareable;


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

    public Entity getShareable() {
        return shareable;
    }

    public void setShareable(Entity shareable) {
        this.shareable = shareable;
    }



    public void setReceivedAt(Date receivedAt) {
        this.receivedAt = receivedAt;
    }

    public Date getReceivedAt() {
        return receivedAt;
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



   @PostConstruct
   protected void initSender() {
       setSender(getCurrentUser());
   }

    private User getCurrentUser() {
        return getUserSessionSource().getUserSession().getCurrentOrSubstitutedUser();
    }

    private UserSessionSource getUserSessionSource() {
        return AppBeans.get(UserSessionSource.class);
    }
}