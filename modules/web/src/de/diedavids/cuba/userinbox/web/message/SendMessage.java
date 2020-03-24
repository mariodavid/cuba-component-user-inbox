package de.diedavids.cuba.userinbox.web.message;

import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.global.CommitContext;
import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.userinbox.entity.SendMessageEntity;
import de.diedavids.cuba.userinbox.service.MessageService;

import javax.inject.Inject;
import java.util.Collections;
import java.util.Set;

@UiController("ddcui$send-message")
@UiDescriptor("send-message.xml")
@EditedEntityContainer("messageDc")
@LoadDataBeforeShow
public class SendMessage extends StandardEditor<SendMessageEntity> {

    @Inject
    protected MessageService messageService;

    @Install(target = Target.DATA_CONTEXT)
    protected Set<Entity> commitDelegate(CommitContext commitContext) {
        messageService.sendMessage(getEditedEntity());
        return Collections.singleton(getEditedEntity());
    }


}