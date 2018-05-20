package de.diedavids.cuba.userinbox.web.action

import com.haulmont.cuba.gui.components.Button
import com.haulmont.cuba.gui.components.ButtonsPanel
import com.haulmont.cuba.gui.components.ListComponent
import com.haulmont.cuba.gui.components.Window
import de.balvi.cuba.declarativecontrollers.web.annotationexecutor.browse.BrowseAnnotationExecutor
import de.balvi.cuba.declarativecontrollers.web.helper.ButtonsPanelHelper
import de.diedavids.cuba.userinbox.web.Shareable
import groovy.transform.CompileStatic
import org.springframework.stereotype.Component

import javax.inject.Inject
import java.lang.annotation.Annotation

@CompileStatic
@Component('ddcui$ShareableBrowseAnnotationExecutor')
class ShareableBrowseAnnotationExecutor implements BrowseAnnotationExecutor<Shareable> {

    static final List<String> PRE_BUTTONS = [
            'createBtn',
            'editBtn',
            'removeBtn',
            'refreshBtn',
            'excelBtn',
            'reportBtn',
    ].asImmutable()


    @Inject
    ButtonsPanelHelper buttonsPanelHelper

    @SuppressWarnings('Instanceof')
    boolean supports(Annotation annotation) {
        annotation instanceof Shareable
    }

    @Override
    void init(Shareable annotation, Window.Lookup browse, Map<String, Object> params) {
        ListComponent listComponent = browse.getComponent(annotation.listComponent()) as ListComponent
        def action = new TableShareableAction(listComponent)
        listComponent.addAction(action)
        if (annotation.buttonsPanel()) {
            ButtonsPanel buttonsPanel = browse.getComponent(annotation.buttonsPanel()) as ButtonsPanel
            Button destinationBtn = buttonsPanelHelper.createButton(annotation.buttonId(), buttonsPanel, PRE_BUTTONS)
            destinationBtn.action = action
        }
    }

    @Override
    void ready(Shareable annotation, Window.Lookup browse, Map<String, Object> params) {

    }
}