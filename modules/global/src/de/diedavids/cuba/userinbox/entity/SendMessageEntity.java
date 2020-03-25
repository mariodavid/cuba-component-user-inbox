package de.diedavids.cuba.userinbox.entity;

import com.haulmont.chile.core.annotations.MetaClass;
import com.haulmont.chile.core.annotations.MetaProperty;
import com.haulmont.cuba.core.entity.BaseDbGeneratedIdEntity;
import com.haulmont.cuba.core.entity.BaseUuidEntity;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.entity.annotation.SystemLevel;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.security.entity.User;
import de.diedavids.cuba.entitysoftreference.EntitySoftReferenceConverter;

import javax.annotation.PostConstruct;
import javax.persistence.Convert;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@MetaClass(name = "ddcui_SendMessageEntity")
public class SendMessageEntity extends BaseUuidEntity {
    private static final long serialVersionUID = 2822107167183964468L;

    @Size(min = 1)
    @NotNull
    @MetaProperty(mandatory = true)
    protected List<User> receivers;

    @SystemLevel
    @Convert(converter = EntitySoftReferenceConverter.class)
    @MetaProperty(datatype = "EntitySoftReference")
    protected Entity shareable;

    @MetaProperty
    protected String subject;

    @MetaProperty
    protected String text;

    @MetaProperty
    protected User sender;

    public Entity getShareable() {
        return shareable;
    }

    public void setShareable(Entity shareable) {
        this.shareable = shareable;
    }

    public void setReceivers(List<User> receivers) {
        this.receivers = receivers;
    }

    public List<User> getReceivers() {
        return receivers;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @PostConstruct
    public void postConstruct(UserSessionSource userSessionSource) {
        setSender(
                userSessionSource.getUserSession().getCurrentOrSubstitutedUser()
        );
    }
}