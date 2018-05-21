# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [0.3.0] - 2018-05-21

### Added
- `@Shareable` annotation share entity instances with other users in any screen 

### Change
- BREAKING: Storage format for message record references has been changed. In order to migrate to the new storage format
you have to download [etc/migrate-message-records-to-new-format.zip](
) from this repository and run it in the [Diagnose Wizard](https://github.com/mariodavid/cuba-component-runtime-diagnose/tree/de284e5cbce19bb02260b59b9dcd133f2729b0e3#diagnose-wizard).
For that to work you have to temporarily add the [runtime-diagnose](https://github.com/mariodavid/cuba-component-runtime-diagnose) app-component to your application.
This script has to run once, will do the migration. After that, the app component can be removed again.

## [0.2.0] - 2018-05-09

### Added
- configurable timers for refresh period of unread messages
- mark messages as read once they are opened

## [0.1.0] - 2018-02-07

### Added
- user messages
- programmatic access to user messages
- messages with entity references


### Dependencies
- CUBA 6.8.x
