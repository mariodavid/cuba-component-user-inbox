# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/)
and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [0.8.0] - 2020-03-25

### Added
- ability to send message to multiple receivers

### Changed
- Refactored all Screens to CUBA 7 APIs

### Dependencies
- CUBA 7.2.x

## [0.7.0] - 2020-03-21

### Added
- Support for PostgreSQL
- Support for MySQL

### Dependencies
- CUBA 7.1.x

## [0.6.0] - 2019-05-14

### Added
- Support for CUBA 7
- `WithEntitySharingSupport` interface for CUBA 7 UI browse screens as a replacement for `@Shareable`

### Dependencies
- CUBA 7.0.x
- declarative-controllers 0.8.0
- entity-soft-reference 0.5.1

## [0.5.0] - 2019-03-14

### Dependencies
- CUBA 6.10.x
- declarative-controllers 0.7.0
- entity-soft-reference 0.4.0

## [0.4.0] - 2018-07-13

### Change
- show sender attribute in show-message screen

### Dependencies
- CUBA 6.9.x

## [0.3.0] - 2018-05-21

### Added
- `@Shareable` annotation share entity instances with other users in any screen 

### Change
- BREAKING: Storage format for message record references has been changed. In order to migrate to the new storage format
you have to download [migrate-message-records-to-new-format.zip](
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
