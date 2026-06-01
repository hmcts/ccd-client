# Core case data client

This is a client library for interacting with the core case data store application. The two main responsibilities are:
 - start event for new case
 - submit case to core case data app

## Getting started

This library is hosted on Azure DevOps Artifacts and can be used in your project by adding the following to your `build.gradle` file:

```gradle
repositories {
    maven {
        url 'https://pkgs.dev.azure.com/hmcts/Artifacts/_packaging/hmcts-lib/maven/v1'
    }
}

dependencies {
  implementation 'com.github.hmcts:core-case-data-store-client:LATEST_TAG'
}
```

### Prerequisites

- [Java 17](https://adoptium.net/temurin/releases/)

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

## Functional tests against AAT

Create a `.env` file with the required AAT URLs and credentials:

```bash
# IDAM / S2S
IDAM_S2S_AUTH_TOTP_SECRET=
IDAM_S2S_AUTH_MICROSERVICE=
IDAM_S2S_AUTH_URL=

IDAM_API_URL=
IDAM_CLIENT_REDIRECT_URI=
IDAM_CLIENT_ID=
IDAM_CLIENT_SECRET=

# CCD
CORE_CASE_DATA_API_URL=
```

Then run:

```bash
set -a
source .env
set +a

./gradlew functionalTest
```

## Functional tests using CFTLib

The functional tests rely on CCD and IDAM and need to be configured with appropriate user roles and events.

Before running the functional tests against a local CFTLib environment, run:

```bash
./scripts/create-role.sh
./scripts/import-definition.sh
```

Then run:

```bash
./gradlew functionalTest
```

## Versioning

We use [SemVer](http://semver.org/) for versioning.
For the versions available, see the tags on this repository.

To release a new version add a tag with the version number and push this up to the origin repository. This will then 
build and publish the release to maven.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE.md) file for details.
