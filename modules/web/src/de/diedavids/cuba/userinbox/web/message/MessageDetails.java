package de.diedavids.cuba.userinbox.web.message;

import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.userinbox.entity.Message;

import javax.inject.Inject;

@UiController("ddcui$message-details")
@UiDescriptor("message-details.xml")
@EditedEntityContainer("messageDc")
@LoadDataBeforeShow
public class MessageDetails extends StandardEditor<Message> {

    @Inject
    protected DataContext dataContext;

    @Subscribe
    protected void onAfterShow(AfterShowEvent event) {
        getEditedEntity().setRead(true);
        dataContext.commit();
    }

}