[![license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](http://www.apache.org/licenses/LICENSE-2.0)
[![Build Status](https://travis-ci.org/mariodavid/cuba-component-health-check.svg?branch=master)](https://travis-ci.org/mariodavid/cuba-component-health-check)

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
| 6.6.x            | 0.1.x          |

The latest version is: `0.1.0`

Add custom application component to your project:

* Artifact group: `de.diedavids.cuba.userinbox`
* Artifact name: `user-inbox-global`
* Version: *add-on version*


## Using the inbox as a user

A user
![Screenshot health check overview](https://github.com/mariodavid/cuba-component-health-check/blob/master/img/health-check-overview.png)

