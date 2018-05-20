package de.diedavids.cuba.userinbox.web.action

import com.haulmont.chile.core.model.MetaClass
import com.haulmont.cuba.core.global.AppBeans
import com.haulmont.cuba.gui.components.Component
import com.haulmont.cuba.gui.components.ListComponent
import com.haulmont.cuba.gui.components.actions.ItemTrackingAction

class TableShareableAction extends ItemTrackingAction {


    ShareableBean withEntityMessageBean = AppBeans.<ShareableBean> get(ShareableBean)


    @SuppressWarnings('ThisReferenceEscapesConstructor')
    TableShareableAction(ListComponent listComponent) {
        super(ShareableBean.ACTION_ID)
        target = listComponent
        withEntityMessageBean.setIcon(this)
        withEntityMessageBean.setCaption(this)
    }

    @Override
    void actionPerform(Component component) {
        withEntityMessageBean.openSendMessageEditor(target.frame, target.singleSelected)
    }

    MetaClass getTargetMetaClass() {
        target.datasource.metaClass
    }
}