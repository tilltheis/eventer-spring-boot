# Eventer

Create events, invite friends and party hard!

This is a playground to get to know [Spring](https://spring.io).

## Development

Prerequisites: `docker`, `docker-compose` and `gradle` must be installed.

Start the DB with `POSTGRES_PASSWORD=XXX docker-compose up`.
Start the app with `POSTGRES_PASSWORD=XXX ./gradlew bootRun`, it will listen on `http://localhost:8080`.

_Hint: When developing in Intellij on Windows, hit `Ctl+F9` to trigger a build and let Spring re-compile the app on the fly._