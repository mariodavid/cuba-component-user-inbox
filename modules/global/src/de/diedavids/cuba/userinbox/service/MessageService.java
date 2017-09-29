package de.diedavids.cuba.userinbox.service;


import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.security.entity.User;

public interface MessageService {
    String NAME = "ddcui_MessageService";

    void sendMessage(User receiver, String subject, String messageText, Entity entityReference);
    void sendMessage(User receiver, String subject, String messageText);


    int countUnreadMessagesForCurrentUser();
}