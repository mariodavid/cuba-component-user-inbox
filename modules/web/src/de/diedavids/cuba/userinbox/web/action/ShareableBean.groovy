package de.diedavids.cuba.userinbox.web.action

import com.haulmont.cuba.core.entity.Entity
import com.haulmont.cuba.core.global.Messages
import com.haulmont.cuba.core.global.Metadata
import com.haulmont.cuba.core.global.UserSessionSource
import com.haulmont.cuba.gui.WindowManager
import com.haulmont.cuba.gui.components.Action
import com.haulmont.cuba.gui.components.Frame
import de.diedavids.cuba.userinbox.entity.Message
import groovy.transform.CompileStatic
import org.springframework.stereotype.Component

import javax.inject.Inject

@CompileStatic
@Component('ddcdi$WithEntityMessageBean')
class ShareableBean {

    public  static final String ACTION_ID = 'share'


    private static final String IMPORT_CAPTION_MSG_KEY = 'actions.Share'
    private static final String IMPORT_ICON_KEY = 'font-icon:SHARE_ALT'

    private static final String SHARE_SUBJECT_KEY = 'share.subject'
    private static final String SHARE_TEXT_KEY = 'share.text'


    @Inject
    Messages messages

    @Inject
    Metadata metadata

    @Inject
    UserSessionSource userSessionSource


    void setCaption(Action action) {
        action.setCaption(messages.getMainMessage(IMPORT_CAPTION_MSG_KEY))
    }

    void setIcon(Action action) {
        action.setIcon(IMPORT_ICON_KEY)
    }


    void openSendMessageEditor(Frame frame, Entity shareable) {
        frame.openEditor('send-message', createShareMessage(shareable), WindowManager.OpenType.DIALOG)
    }

    private Message createShareMessage(Entity shareable) {
        def newMessage = metadata.create(Message)

        newMessage.shareable = shareable
        newMessage.subject = getShareMessageSubject(shareable)
        newMessage.text = getShareMessageText(shareable)

        newMessage
    }

    private String getShareMessageText(Entity shareable) {
        messages.formatMainMessage(SHARE_TEXT_KEY, getEntityCaption(shareable), shareable.instanceName, currentUsername)
    }

    private String getShareMessageSubject(Entity shareable) {
        messages.formatMainMessage(SHARE_SUBJECT_KEY, currentUsername, getEntityCaption(shareable), shareable.instanceName)
    }

    private String getEntityCaption(Entity shareable) {
        messages.tools.getEntityCaption(shareable.metaClass)
    }

    private String getCurrentUsername() {
        userSessionSource.userSession.currentOrSubstitutedUser.name
    }

}