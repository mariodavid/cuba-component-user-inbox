package de.diedavids.cuba.userinbox.service

import com.haulmont.cuba.core.entity.Entity
import com.haulmont.cuba.core.global.*
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

    @Inject
    Security security


    @Override
    void sendSystemMessage(User receiver, String subject, String messageText, Entity entityReference) {
        sendMessageFromUser(null, receiver, subject, messageText, entityReference)
    }

    @Override
    void sendSystemMessage(User receiver, String subject, String messageText) {
        sendMessageFromUser(null, receiver, subject, messageText)
    }

    @Override
    void sendMessage(User receiver, String subject, String messageText, Entity entityReference = null) {
        sendMessageFromUser(currentUser, receiver, subject, messageText, entityReference)
    }

    protected void sendMessageFromUser(User sender, User receiver, String subject, String messageText, Entity entityReference = null) {
        Message message = createMessageInstance()

        setRecordToMessage(entityReference, message)

        message.sender = sender
        message.receiver = receiver

        message.subject = subject
        message.text = messageText

        dataManager.commit(message)
    }

    protected Message createMessageInstance() {
        metadata.create(Message)
    }

    protected void setRecordToMessage(Entity entityReference, Message message) {
        message.entityReferenceClass = entityReference.metaClass.name
        message.entityReferenceId = entityReference.id as String
        message.entityCaption = entityReference.instanceName
    }

    private User getCurrentUser() {
        userSessionSource.userSession.currentOrSubstitutedUser
    }


    @Override
    int countUnreadMessagesForCurrentUser() {
        LoadContext loadContext = LoadContext.create(Message)
                .setQuery(LoadContext.createQuery('select e from ddcui$Message e where e.receiver.id = :userId and e.read = FALSE')
                .setParameter('userId', currentUser.id))

        dataManager.getCount(loadContext)
    }

}