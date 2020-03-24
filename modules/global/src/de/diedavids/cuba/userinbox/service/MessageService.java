package de.diedavids.cuba.userinbox.service;


import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.security.entity.User;
import de.diedavids.cuba.userinbox.entity.SendMessageEntity;

public interface MessageService {
    String NAME = "ddcui_MessageService";

    void sendSystemMessage(User receiver, String subject, String messageText, Entity entityReference);
    void sendSystemMessage(User receiver, String subject, String messageText);

    void sendMessage(User receiver, String subject, String messageText, Entity entityReference);
    void sendMessage(User receiver, String subject, String messageText);


    long countUnreadMessagesForCurrentUser();

    void sendMessage(SendMessageEntity sendMessageEntity);
}