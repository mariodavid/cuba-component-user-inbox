package de.diedavids.cuba.userinbox.web.screens;

import com.haulmont.cuba.gui.components.mainwindow.SideMenu;
import de.diedavids.sneferu.components.descriptor.GenericComponentDescriptor;
import de.diedavids.sneferu.components.testapi.GenericComponentTestAPI;

class SideMenuComponentDescriptor extends GenericComponentDescriptor<SideMenu, GenericComponentTestAPI<SideMenu>> {

    SideMenuComponentDescriptor(String componentId) {
        super(SideMenu.class, componentId);
    }

    @Override
    public GenericComponentTestAPI<SideMenu> createTestAPI(SideMenu component) {
        return new GenericComponentTestAPI<>(component);
    }
}