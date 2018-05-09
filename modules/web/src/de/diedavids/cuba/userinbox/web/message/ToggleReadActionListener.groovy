package de.diedavids.cuba.userinbox.web.message

import com.haulmont.cuba.core.global.Messages
import com.haulmont.cuba.gui.components.Action
import com.haulmont.cuba.gui.components.Frame
import com.haulmont.cuba.gui.data.Datasource
import de.diedavids.cuba.userinbox.entity.Message


class ToggleReadActionListener implements Datasource.ItemChangeListener<Message> {

    Action toggleReadAction
    Messages messages


    @Override
    void itemChanged(Datasource.ItemChangeEvent<Message> e) {
        if (e.item) {
            updateToggleReadAction(e.item)
        }
    }

    private updateToggleReadAction(Message message) {
        if(message.read) {
            toggleReadAction.caption = formatMessage("markAsUnread")
            toggleReadAction.icon = "font-icon:SQUARE_O"
        }
        else {
            toggleReadAction.caption = formatMessage("markAsRead")
            toggleReadAction.icon = "font-icon:CHECK_SQUARE_O"
        }
    }

    private String formatMessage(String messageKey) {
        messages.formatMessage(getClass(), messageKey)
    }
}
