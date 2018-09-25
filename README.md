# Core case data client

[![Build Status](https://travis-ci.org/hmcts/core-case-data-store-client.svg?branch=master)](https://travis-ci.org/hmcts/core-case-data-store-client)
[![Download](https://api.bintray.com/packages/hmcts/hmcts-maven/core-case-data-store-client/images/download.svg) ](https://bintray.com/hmcts/hmcts-maven/core-case-data-store-client/_latestVersion)

This is a client library for interacting with the core case data store application. The two main responsibilities are:
 - start event for new case
 - submit case to core case data app

## Getting started

### Prerequisites

- [JDK 8](https://www.oracle.com/java)
- [Docker](https://www.docker.com)

## Usage

Just include the library as your dependency and you will be to use the client class. Health check for CCD service is provided as well.

Components provided by this library will get automatically configured in a Spring context if `core_case_data.api.url` configuration property is defined and does not equal `false`. 

## Building

The project uses [Gradle](https://gradle.org) as a build tool but you don't have install it locally since there is a
`./gradlew` wrapper script.  

To build project please execute the following command:

```bash
    ./gradlew build
```

## Developing

### Coding style tests

To run all checks (including unit tests) please execute the following command:

```bash
    ./gradlew check
```

## Versioning

We use [SemVer](http://semver.org/) for versioning.
For the versions available, see the tags on this repository.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE.md) file for details.
