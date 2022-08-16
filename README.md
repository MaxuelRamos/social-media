# Social media

This is a coding exercise.

### Features

- Post creation
- Post listing
  - Allow filter by current user
  - Allow filter by date
- User profile query

The application comes with 5 test users. The default authenticated user is the one with id 1.
To change the user, run this command:

`curl --location --request PUT 'localhost:8080/social-media/api/me/<id>`

Replace `<id>` with other user id. Any number between 1 and 5 are valid.

At ./others/postman there is a postman collection with request examples for convenience, but the project also provides a swagger view.

## Requirements

- Maven
- Java 11
- Docker (not needed if maven is used to start the application)

# Run with maven
## Build

> ./mvnw clean install

## Test

> ./mvnw test

## Run

> ./mvnw spring-boot:run

# Run with docker

## Build image

> docker build -t maxuelramos/social-media . --build-arg VERSION=1.0.0-SNAPSHOT

## Compose

> docker-compose -f ./docker/docker-compose.yml up -d

