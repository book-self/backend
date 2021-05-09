# Book Self
## A Books Recommendation Web Application

#### SE491 - Software Engineering Studio
**DePaul University, College of Computing and Digital Media**

*Spring 2021*

## Team Members
1. David Engel
2. Christian Kleinvehn
3. Kyle Olson
4. Jared Schreiber
5. Lisa Sun
6. Nardos Tessema

## Development
### Requirements
JDK 11 is required to build and run this application.

Since we're using the Gradle Wrapper, there is no need to install Gradle locally. Just use `./gradlew` or `gradle.bat`.

### Required Environment Variables
Environment Variable | Default | Description
-------------------- | ------- | -----------
`BOOKSELF_DB_HOST` | `localhost` | The database host
`BOOKSELF_DB_NAME` | `bookself` | The database name
`BOOKSELF_DB_PORT` | `5432` | The database port. Defaults to `5432`
`BOOKSELF_DB_USER` | `postgres` | The db user name
`BOOKSELF_DB_PASS` |  | The db password
`BOOKSELF_CORS_ALLOWED_ORIGINS` | `*` | CORS allowed origins (Should be the front-end url unless we want to allow all origins)

### Running the Backend Application
`$ ./gradlew :apis:bootRun`

__OR, while in the same directory as the executable JAR,__

`$ java -jar apis-0.0.1-SNAPSHOT.jar`

### Endpoints

Endpoint | Description
-------- | -----------
`/ping` | Health Check endpoint
`/v1/books/{id}` | Get a book by id
`/v1/books/all` | Get random books (the limit is configurable)
`/v1/books/by-author?authorId=1234` | Get book by author
`/v1/books/by-genre?genre=Some+Genre` | Get a book by genre (Works with a single genre only)
`/v1/genres/all` | Get random genres (the limit is configurable)
`/v1/authors/{id}` | Get author by id

