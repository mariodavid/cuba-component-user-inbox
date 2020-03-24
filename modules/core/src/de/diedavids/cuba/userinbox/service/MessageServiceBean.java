package de.diedavids.cuba.userinbox.service;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.UserSessionSource;
import com.haulmont.cuba.security.entity.User;
import de.diedavids.cuba.userinbox.entity.Message;
import de.diedavids.cuba.userinbox.entity.SendMessageEntity;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service(MessageService.NAME)
public class MessageServiceBean implements MessageService {

    @Inject
    private DataManager dataManager;
    @Inject
    private Metadata metadata;
    @Inject
    private UserSessionSource userSessionSource;

    @Override
    public void sendSystemMessage(User receiver, String subject, String messageText, Entity entityReference) {
        sendMessageFromUser(null, receiver, subject, messageText, entityReference);
    }

    @Override
    public void sendSystemMessage(User receiver, String subject, String messageText) {
        sendMessageFromUser(null, receiver, subject, messageText);
    }

    @Override
    public void sendMessage(User receiver, String subject, String messageText, Entity entityReference) {
        sendMessageFromUser(getCurrentUser(), receiver, subject, messageText, entityReference);
    }

    @Override
    public void sendMessage(User receiver, String subject, String messageText) {
        sendMessage(receiver, subject, messageText, null);
    }

    protected void sendMessageFromUser(User sender, User receiver, String subject, String messageText, Entity entityReference) {
        Message message = createMessageInstance();

        message.setShareable(entityReference);

        message.setSender(sender);
        message.setReceiver(receiver);

        message.setSubject(subject);
        message.setText(messageText);

        dataManager.commit(message);
    }

    protected void sendMessageFromUser(User sender, User receiver, String subject, String messageText) {
        sendMessageFromUser(sender, receiver, subject, messageText, null);
    }

    protected Message createMessageInstance() {
        return metadata.create(Message.class);
    }

    private User getCurrentUser() {
        return userSessionSource.getUserSession().getCurrentOrSubstitutedUser();
    }

    @Override
    public long countUnreadMessagesForCurrentUser() {
        LoadContext loadContext = LoadContext.create(Message.class).setQuery(LoadContext.createQuery("select e from ddcui$Message e where e.receiver.id = :userId and e.read = FALSE").setParameter("userId", getCurrentUser().getId()));

        return dataManager.getCount(loadContext);
    }

    @Override
    public void sendMessage(SendMessageEntity sendMessageEntity) {
        sendMessageEntity
                .getReceivers()
                .forEach(receiver ->
                        sendMessageFromUser(
                            sendMessageEntity.getSender(),
                            receiver,
                            sendMessageEntity.getSubject(),
                            sendMessageEntity.getText(),
                            sendMessageEntity.getShareable()
                    )
                );
    }
}
