package de.diedavids.cuba.userinbox;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.Default;

@Source(type = SourceType.DATABASE)
public interface UserInboxConfiguration extends Config {

    @Property("user-inbox.mainwindow.refresh")
    @Default("5000")
    Integer getSqlAllowDataManipulation();

}