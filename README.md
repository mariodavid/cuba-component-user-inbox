[![license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
[![Build Status](https://travis-ci.org/mariodavid/cuba-component-user-inbox.svg?branch=master)](https://travis-ci.org/mariodavid/cuba-component-user-inbox)
[ ![Download](https://api.bintray.com/packages/mariodavid/cuba-components/cuba-component-user-inbox/images/download.svg) ](https://bintray.com/mariodavid/cuba-components/cuba-component-user-inbox/_latestVersion)

# CUBA Platform Component - User Inbox

This CUBA component gives users a mailbox for user to user and system to user messages


## Installation

1. Add the following maven repository `https://dl.bintray.com/mariodavid/cuba-components` to the build.gradle of your CUBA application:



        buildscript {
          repositories {
            maven {
              url  "https://dl.bintray.com/mariodavid/cuba-components"
            }
          }
       }
    

2. Select a version of the add-on which is compatible with the platform version used in your project:

| Platform Version | Add-on Version |
| ---------------- | -------------- |
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

### NOTE: BREAKING CHANGE in 0.3.0
There is a breaking change if you update from 0.2.0 to 0.3.0. The mechanism how the record references are stored to a message
changed. It can be resolved with manual action. See [CHANGELOG.md](https://github.com/mariodavid/cuba-component-user-inbox/blob/master/CHANGELOG.md) for more information.

### NOTE: Dependency: declarative-controllers
This application component requires `declarative-controllers` as another dependency you have to add to your application.

The reason is, that you need to extend your screen from `AnnotatableAbstractLookup` instead of `AbstractLookup`.
This superclass is part of the app-component: [cuba-component-declarative-controllers](https://github.com/balvi/cuba-component-declarative-controllers).

Technically it is not strictly required to directly add the dependency to `declarative-controllers`, since `user-inbox` already has a dependency on it.

However: since you directly depend on the app component (with extending your classes from `AnnotatableAbstractLookup`), 
it is a best practice to explicitly declare the dependency to it.

## Using the inbox as a user

As a logged in user you'll see a menu entry called "Messages" which will show the incoming messages that are either
from another user of the system or from the system itself, which has send a message
to the system in order to notify the user about something changed in the system.

![Screenshot overview of incoming messages](https://github.com/mariodavid/cuba-component-user-inbox/blob/master/img/messages-overview.png)


## Sending messages

To send messages to a particular user, there are two options available:

* manually sending message to a user
* system messages that are send programmatically


### Send manual messages

In order to send a message to a particular user, there is a button "Send message" in the users inbox.

This screen allows to manually send a message. A message contains a subject and a body, just like a regular Email.

![Screenshot of sending a new message](https://github.com/mariodavid/cuba-component-user-inbox/blob/master/img/send-message.png)


This feature can sometimes be helpful but oftentimes sending a regular email is not worse.

#### Context-based messages

Therefore there is another option to send a Message. In this case it is a message that is send through the context of a particular entity.

This is comparable of sending a email with a link that points to a particular customer / order etc. in your application together with the information from the sender.

##### Share entity instances

The way to send context-based messages is to use the `@Shareable` annotation. The annotation is used in any Entity browse / editor screen.

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

### Send system messages (programmatically)

The other way to send a message to a user is that the developer defines points in the application, where it is useful to notify some user
about a particular thing happend. This can be various actions, like:

* a new Customer has been created
* an order was placed with a total amount > 10k
* an import of data through an API was successful
* ...

There are lot of things that might happen in the lifecycle of an application that are worth to notify.

To send a message to user programmatically, there is a [MessageService]() which will allow exactly this. The interface of this service
looks liks this:

````
public interface MessageService {

    void sendSystemMessage(User receiver, String subject, String messageText);
    
    void sendSystemMessage(User receiver, String subject, String messageText, Entity entityReference);

}
````

`sendSystemMessage` will add a message in the given receivers inbox with a subject, a text and an optional entity reference.


## Using predefined main window screens

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
 
##### unread messages counter in predefined main windows screens

In order to display the messages that are marked as unread, the main windows will be refreshed in a particular interval.
The logic is the same as the Count script which is available within the [Application Folders](https://doc.cuba-platform.com/manual-6.8/application_folder.html)
feature of CUBA itself. 

Therefore the refresh period can be adjusted by the existing application property:
[cuba.web.appFoldersRefreshPeriodSec](https://doc.cuba-platform.com/manual-6.8/app_properties_reference.html#cuba.web.appFoldersRefreshPeriodSec)
in the `web-app.properties` file.
