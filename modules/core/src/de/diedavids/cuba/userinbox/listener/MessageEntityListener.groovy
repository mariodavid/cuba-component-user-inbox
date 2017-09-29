package de.diedavids.cuba.userinbox.listener

import com.haulmont.cuba.core.global.UserSessionSource
import com.haulmont.cuba.security.entity.User
import org.springframework.stereotype.Component
import com.haulmont.cuba.core.listener.BeforeInsertEntityListener
import com.haulmont.cuba.core.EntityManager
import de.diedavids.cuba.userinbox.entity.Message

import javax.inject.Inject

@Component("ddcui_MessageEntityListener")
public class MessageEntityListener implements BeforeInsertEntityListener<Message> {

    @Inject
    UserSessionSource userSessionSource

    @Override
    public void onBeforeInsert(Message entity, EntityManager entityManager) {
        entity.sender = currentUser
    }

    private User getCurrentUser() {
        userSessionSource.userSession.currentOrSubstitutedUser
    }

}