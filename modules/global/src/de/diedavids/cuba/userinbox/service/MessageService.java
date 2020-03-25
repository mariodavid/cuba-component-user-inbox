package de.diedavids.cuba.userinbox.service;


import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.security.entity.User;
import de.diedavids.cuba.userinbox.entity.SendMessageEntity;

public interface MessageService {
    String NAME = "ddcui_MessageService";


    /**
     * sends a message to a user as system (no sender defined in the message)
     *
     * @param receiver the user as the receiver of the message
     * @param subject the subject of the message
     * @param messageText the message text
     * @param entityReference the shareable entity reference
     */
    void sendSystemMessage(User receiver, String subject, String messageText, Entity entityReference);


    /**
     * sends a message to a user as system (no sender defined in the message)
     *
     * @param receiver the user as the receiver of the message
     * @param subject the subject of the message
     * @param messageText the message text
     */
    void sendSystemMessage(User receiver, String subject, String messageText);

    /**
     * sends a message to a user with the current user as the sender
     *
     * @param receiver the user as the receiver of the message
     * @param subject the subject of the message
     * @param messageText the message text
     * @param entityReference the shareable entity reference
     */
    void sendMessage(User receiver, String subject, String messageText, Entity entityReference);


    /**
     * sends a message to a user with the current user as the sender
     *
     * @param receiver the user as the receiver of the message
     * @param subject the subject of the message
     * @param messageText the message text
     */
    void sendMessage(User receiver, String subject, String messageText);


    /**
     * returns the count of unread messages for the current user
     * @return the count of unread messages
     */
    long countUnreadMessagesForCurrentUser();

    /**
     * sends a message using the SendMessageEntity, which allows to define:
     * - receivers
     * - sender
     * - subject
     * - text
     * - shareable entity reference
     *
     * @param sendMessageEntity the SendMessageEntity
     */
    void sendMessage(SendMessageEntity sendMessageEntity);
}