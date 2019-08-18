# Eventer

Create events, invite friends and party hard!

This is a playground to get to know [Spring](https://spring.io).

## Development

Have a PostgreSQL server running on port `5432` with a user `eventer` and a database `eventer`.
The password will be read from an environment variable.
The app can be run with `DATABASE_PASSWORD='<secret>' ./gradlew bootRun` and will listen on `http://localhost:8080`.

_Hint: When developing in Intellij on Windows, hit `Ctl+F9` to trigger a build and let Spring re-compile the app on the fly._