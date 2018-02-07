[![license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
[![Build Status](https://travis-ci.org/mariodavid/cuba-component-user-inbox.svg?branch=master)](https://travis-ci.org/mariodavid/cuba-component-user-inbox)
[ ![Download](https://api.bintray.com/packages/mariodavid/cuba-components/cuba-component-user-inbox/images/download.svg) ](https://bintray.com/mariodavid/cuba-components/cuba-component-user-inbox/_latestVersion)

# CUBA Platform Component - User Inbox

This CUBA component gives users a mailbox for user to user and system to user messages


## Installation

1. Add the following maven repository `https://dl.bintray.com/mariodavid/cuba-components` to the build.gradle of your CUBA application:


    buildscript {
        
        //...
        
        repositories {
        
            // ...
        
            maven {
                url  "https://dl.bintray.com/mariodavid/cuba-components"
            }
        }
        
        // ...
    }

2. Select a version of the add-on which is compatible with the platform version used in your project:

| Platform Version | Add-on Version |
| ---------------- | -------------- |
| 6.8.x            | 0.1.x          |

The latest version is: [ ![Download](https://api.bintray.com/packages/mariodavid/cuba-components/cuba-component-user-inbox/images/download.svg) ](https://bintray.com/mariodavid/cuba-components/cuba-component-user-inbox/_latestVersion)

Add custom application component to your project:

* Artifact group: `de.diedavids.cuba.userinbox`
* Artifact name: `user-inbox-global`
* Version: *add-on version*



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

##### Context-based messages

Therefore there is another option to send a Message. In this case it is a message that is send through the context of a particular entity.

This is comparable of sending a email with a link that points to a particular customer / order etc. in your application together with the information from the sender.


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
 
 
