package de.diedavids.cuba.userinbox.web.message

import com.haulmont.cuba.gui.components.AbstractLookup
import com.haulmont.cuba.gui.components.Component
import com.haulmont.cuba.gui.components.Table
import com.haulmont.cuba.gui.components.actions.BaseAction
import com.haulmont.cuba.gui.components.actions.CreateAction
import com.haulmont.cuba.gui.data.CollectionDatasource
import de.diedavids.cuba.userinbox.entity.Message

import javax.annotation.Nullable
import javax.inject.Inject
import javax.inject.Named

class UserInbox extends AbstractLookup {

    @Named("messagesTable.create")
    CreateAction createAction

    @Inject
    Table<Message> messagesTable

    @Inject
    CollectionDatasource<Message, UUID> messagesDs

    @Override
    void init(Map<String, Object> params) {
        super.init(params)

        createAction.windowId = 'send-message'

        messagesTable.setItemClickAction(new BaseAction('mark-message-as-read') {
            @Override
            void actionPerform(Component component) {
                messagesTable.singleSelected.read = true
                messagesDs.commit()
            }
        })

        messagesTable.setStyleProvider(new Table.StyleProvider<Message>() {
            @Override
            String getStyleName(Message entity, @Nullable String property) {
                "bold"
            }
        })
    }
}