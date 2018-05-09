package de.diedavids.cuba.userinbox.web.message

import com.haulmont.cuba.gui.components.AbstractEditor
import com.haulmont.cuba.gui.data.Datasource
import de.diedavids.cuba.userinbox.entity.Message

import javax.inject.Inject

class MessageEdit extends AbstractEditor<Message> {


    @Inject
    Datasource<Message> messageDs

    @Override
    protected void postInit() {
        item.read = true
        messageDs.commit()
    }
}