[![license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
[![Build Status](https://travis-ci.org/mariodavid/cuba-component-user-inbox.svg?branch=master)](https://travis-ci.org/mariodavid/cuba-component-user-inbox)
[ ![Download](https://api.bintray.com/packages/mariodavid/cuba-components/cuba-component-user-inbox/images/download.svg) ](https://bintray.com/mariodavid/cuba-components/cuba-component-user-inbox/_latestVersion)

# CUBA Platform Component - User Inbox

This CUBA component gives users a mailbox for user to user and system to user messages


![send message manually](/img/1-send-message-manually-overview.gif)

## Installation

1. `user-inbox` is available in the [CUBA marketplace](https://www.cuba-platform.com/marketplace/user-inbox/)
2. Select a version of the add-on which is compatible with the platform version used in your project:

| Platform Version | Add-on Version |
| ---------------- | -------------- |
| 7.2.x            | 0.8.x          |
| 7.1.x            | 0.7.x          |
| 7.0.x            | 0.6.x          |
| 6.10.x           | 0.5.x          |
| 6.9.x            | 0.4.x          |
| 6.8.x            | 0.1.x - 0.3.x  |

The latest version is: [ ![Download](https://api.bintray.com/packages/mariodavid/cuba-components/cuba-component-user-inbox/images/download.svg) ](https://bintray.com/mariodavid/cuba-components/cuba-component-user-inbox/_latestVersion)

Add custom application component to your project:

* Artifact group: `de.diedavids.cuba.userinbox`
* Artifact name: `user-inbox-global`
* Version: *add-on version*

```groovy
dependencies {
  appComponent("de.diedavids.cuba.userinbox:user-inbox-global:*addon-version*")
}
```


### CHANGELOG

Information on changes that happen through the different versions of the application component can be found in the [CHANGELOG](CHANGELOG.md).
The Changelog also contains information about breaking changes and tips on how to resolve them.


## Supported DBMS

The following databases are supported by this application component:

* HSQLDB
* PostgreSQL
* MySQL

All other DBMS systems are also possible to work with by the fact that CUBA studio generates the corresponding 
init / update scripts within the application.

## Example Usage

To see this application component in action, check out this example: [cuba-example-using-user-inbox](https://github.com/mariodavid/cuba-example-using-user-inbox).

## Using the Inbox as a User

As a logged in user you'll see a menu entry called "Messages" which will show the incoming messages that are either
from another user of the system or from the system itself, which has send a message
to the system in order to notify the user about something changed in the system.

![user inbox](/img/4-user-inbox.png)

## Sending messages

To send messages to a particular user, there are two options available:

* manually sending message to a user
* system messages that are send programmatically


### Send manual messages

In order to send a message to a particular user, there is a button "Send message" in the users inbox.

This screen allows to manually send a message. A message contains a subject and a body, just like a regular Email.

![send message manually](/img/1-send-message-manually-editor.png)

#### Context-based messages

Therefore there is another option to send a Message. In this case it is a message that is send through the context of a particular entity.

This is comparable of sending a email with a link that points to a particular customer / order etc. in your application together with the information from the sender.

![share entities overview](/img/3-share-entities-overview.gif)

##### Share entity instances (CUBA 6 Screens)

The way to send context-based messages is to use the `@Shareable` annotation for CUBA 6 based screens (AbstractLookup / AbstractEditor).
The annotation is used in any Entity browse / editor screen.

Example:

```
@Shareable(listComponent = "customersTable")
public class CustomerBrowse extends AnnotatableAbstractLookup {
}
```

For the `@Shareable` annotation you need to define the list component on which it should add the share button.
Normally this is the `id` of the table you defined in your browse screen.

This annotation will create a button in the buttonsPanel of the table and add the share button after the default CUBA buttons.

The `@Shareable` annotations can be customized through the following attributes:

* `String listComponent` - the id of the list component / table where the button will be added - REQUIRED
* `String buttonId` - the id of the newly created button that will be created ("attachmentBtn" by default)
* `String buttonsPanel` - the id of the buttons panel where the new button will be added ("buttonsPanel" by default)


more information on this topic can be found here: [balvi/declarative-controllers](https://github.com/balvi/cuba-component-declarative-controllers)


##### Share entity instances (CUBA 7 Screens)

The way to send context-based messages is to use the `WithEntitySharingSupport` interface.
The interface can be used in any Entity browse screen. It requires to specify the table component as well as the buttonsPanel of the table.


Example:

```
import de.diedavids.cuba.userinbox.web.WithEntitySharingSupport

public class CustomerBrowse extends StandardLookup<Customer> implements WithEntitySharingSupport {

    @Inject
    protected GroupTable<Customer> customersTable;


    @Inject
    protected ButtonsPanel buttonsPanel;

    @Override
    public Table getListComponent() {
        return customersTable;
    }

    @Override
    public ButtonsPanel getButtonsPanel() {
        return buttonsPanel;
    }
}
```

This interface will create a button in the buttonsPanel of the table and add the share button after the default CUBA buttons.

### Send system messages (programmatically)

The other way to send a message to a user is that the developer defines points in the application, where it is useful to notify some user
about a particular thing happened. This can be various actions, like:

* a new Customer has been created
* an order was placed with a total amount > 10k
* an import of data through an API was successful
* ...

There are lot of things that might happen in the lifecycle of an application that are worth to notify.

To send a message to user programmatically, there is a [MessageService](https://github.com/mariodavid/cuba-component-user-inbox/blob/master/modules/global/src/de/diedavids/cuba/userinbox/service/MessageService.java) which will allow exactly this. The interface of this service
looks like this:

````
public interface MessageService {

    void sendSystemMessage(User receiver, String subject, String messageText);
    
    void sendSystemMessage(User receiver, String subject, String messageText, Entity entityReference);

}
````

`sendSystemMessage` will add a message in the given receivers inbox with a subject, a text and an optional entity reference.


![programmatic notification sending](/img/5-programmatic-notification-sending-overview.gif)


## Using pre-defined Main Window Screens

This application component comes with two options for the main screens, that can be used in the final application.
 
* SideMainwindowWithMessages (side-mainwindow-with-messages.xml)
* AppMainWindowWithMessages (mainwindow-with-messages.xml)

One of these two classes can be used as the mainwinow through the following definition in your web-screens.xml:

````    
<!-- either the normal mainwindow with messages badge -->
<screen id="mainWindow"
        template="de/diedavids/cuba/userinbox/web/screens/mainwindow-with-messages.xml"/>

<!-- or the side menu main window with messages badge -->
<screen id="mainWindow"
        template="de/diedavids/cuba/userinbox/web/screens/side-mainwindow-with-messages.xml"/>

````

You can also extend this screens, so that you can add your own (screen-) logic to the mainwindow.
 
##### Unread Messages Counter in pre-defined Main Window Screens

In order to display the messages that are marked as unread, the main windows will be refreshed in a particular interval.
The logic is the same as the Count script which is available within the [Application Folders](https://doc.cuba-platform.com/manual-6.8/application_folder.html)
feature of CUBA itself. 

Therefore the refresh period can be adjusted by the existing application property:
[cuba.web.appFoldersRefreshPeriodSec](https://doc.cuba-platform.com/manual-6.8/app_properties_reference.html#cuba.web.appFoldersRefreshPeriodSec)
in the `web-app.properties` file.
