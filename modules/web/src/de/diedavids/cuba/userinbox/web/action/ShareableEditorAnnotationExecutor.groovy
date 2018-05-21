package de.diedavids.cuba.userinbox.web.action

import com.haulmont.cuba.gui.components.Button
import com.haulmont.cuba.gui.components.Window
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.editor.EditorAnnotationExecutor
import de.balvi.cuba.declarativecontrollers.web.helper.ButtonsPanelHelper
import de.diedavids.cuba.userinbox.web.Shareable
import groovy.transform.CompileStatic
import org.springframework.stereotype.Component

import javax.inject.Inject
import java.lang.annotation.Annotation

@CompileStatic
@Component('ddcui$ShareableEditorAnnotationExecutor')
class ShareableEditorAnnotationExecutor implements EditorAnnotationExecutor<Shareable> {

    @Inject
    ButtonsPanelHelper buttonsPanelHelper

    @SuppressWarnings('Instanceof')
    boolean supports(Annotation annotation) {
        annotation instanceof Shareable
    }

    @Override
    void init(Shareable annotation, Window.Editor editor, Map<String, Object> params) {
        def action = new EditorShareableAction(editor)
        if (annotation.buttonsPanel()) {
            Button destinationBtn = buttonsPanelHelper.getOrCreateButton(editor, annotation.buttonId(), annotation.buttonsPanel())
            destinationBtn.action = action
        }
    }

    @Override
    void postInit(Shareable annotation, Window.Editor editor) {

    }
}