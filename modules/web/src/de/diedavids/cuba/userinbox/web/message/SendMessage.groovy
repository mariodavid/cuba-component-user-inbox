package de.diedavids.cuba.userinbox.web.message

import com.haulmont.cuba.gui.components.AbstractEditor
import com.haulmont.cuba.security.global.UserSession
import de.diedavids.cuba.userinbox.entity.Message

import javax.inject.Inject

class SendMessage extends AbstractEditor<Message> {


    @Inject
    UserSession userSession

    @Override
    protected boolean preCommit() {
        item.sender = userSession.currentOrSubstitutedUser
        return super.preCommit()
    }
}