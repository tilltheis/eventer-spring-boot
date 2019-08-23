# Eventer

Create events, invite friends and party hard!

This is a playground to get to know [Spring Boot](https://spring.io).


## Development

Prerequisites: `docker`, `docker-compose` and `gradle` must be installed.

Start the DB with `docker-compose up`.
Start the app with `./gradlew bootRun`, it will listen on `http://localhost:8080`.

_Hint: When developing in Intellij on Windows, hit `Ctl+F9` to trigger a build and let Spring re-compile the app on the fly._


## Features

- [x] create events
- [ ] invite internal guests (app-internal "friends")
- [x] invite external guests (via email)
- [ ] user registration
- [x] user login
- [ ] guest login
- [ ] event locations (online + offline)
- [ ] view events w/o editing


## Outlook

* fancy frontend (maybe React)
* manual schema (Flyway)