package de.diedavids.cuba.userinbox.web.action

import com.haulmont.cuba.core.global.AppBeans
import com.haulmont.cuba.gui.components.Component
import com.haulmont.cuba.gui.components.Window
import com.haulmont.cuba.gui.components.actions.BaseAction

class EditorShareableAction extends BaseAction {


    ShareableBean shareableBean = AppBeans.<ShareableBean> get(ShareableBean)


    protected Window.Editor editor

    EditorShareableAction(Window.Editor editor) {
        this(ShareableBean.ACTION_ID, editor)
    }


    @SuppressWarnings('ThisReferenceEscapesConstructor')
    EditorShareableAction(String id, Window.Editor editor) {
        super(id)


        this.editor = editor

        updateCaption()
        shareableBean.setIcon(this)
    }

    void updateCaption() {
        shareableBean.setCaption(this)
    }

    @Override
    void actionPerform(Component component) {
        shareableBean.openSendMessageEditor(editor.frame, editor.item)
    }
}