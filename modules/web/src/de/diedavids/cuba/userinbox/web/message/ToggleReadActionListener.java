package de.diedavids.cuba.userinbox.web.message;

import com.haulmont.cuba.core.global.Messages;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.data.Datasource;
import com.haulmont.cuba.gui.model.InstanceContainer;
import de.diedavids.cuba.userinbox.entity.Message;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;

import java.util.function.Consumer;

public class ToggleReadActionListener implements Consumer<InstanceContainer.ItemChangeEvent<Message>> {

    private Action toggleReadAction;
    private Messages messages;


    public ToggleReadActionListener(Messages messages, Action toggleReadAction) {

        this.messages = messages;
        this.toggleReadAction = toggleReadAction;
    }

    @Override
    public void accept(InstanceContainer.ItemChangeEvent<Message> messageItemChangeEvent) {
        if (messageItemChangeEvent.getItem() != null) {
            updateToggleReadAction(messageItemChangeEvent.getItem());
        }
    }

    private void updateToggleReadAction(Message message) {
        if (message.getRead()) {
            toggleReadAction.setCaption(formatMessage("markAsUnread"));
            toggleReadAction.setIcon("font-icon:SQUARE_O");
        } else {
            toggleReadAction.setCaption(formatMessage("markAsRead"));
            toggleReadAction.setIcon("font-icon:CHECK_SQUARE_O");
        }

    }

    private String formatMessage(String messageKey) {
        return messages.formatMessage(getClass(), messageKey);
    }
}
