package de.diedavids.cuba.userinbox.service

import com.haulmont.cuba.core.entity.Entity
import com.haulmont.cuba.core.global.DataManager
import com.haulmont.cuba.core.global.LoadContext
import com.haulmont.cuba.core.global.Metadata
import com.haulmont.cuba.core.global.UserSessionSource
import com.haulmont.cuba.security.entity.User
import de.diedavids.cuba.userinbox.entity.Message
import org.springframework.stereotype.Service

import javax.inject.Inject

@Service(MessageService.NAME)
class MessageServiceBean implements MessageService {

    @Inject
    DataManager dataManager

    @Inject
    Metadata metadata

    @Inject
    UserSessionSource userSessionSource

    @Override
    void sendMessage(User receiver, String subject, String messageText, Entity entityReference = null) {

        Message message = metadata.create(Message.class)

        message.entityReferenceClass = entityReference.metaClass.name
        message.entityReferenceId = entityReference.id as String

        message.receiver = receiver

        message.subject = subject
        message.text = messageText

        dataManager.commit(message)
    }

    private User getCurrentUser() {
        userSessionSource.userSession.currentOrSubstitutedUser
    }


    @Override
    int countUnreadMessagesForCurrentUser() {
        LoadContext loadContext = LoadContext.create(Message)
                .setQuery(LoadContext.createQuery('select e from ddcui$Message e where e.receiver.id = :userId and e.read = FALSE')
                .setParameter("userId", currentUser.id))

        dataManager.getCount(loadContext)
    }

}