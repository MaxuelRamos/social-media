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


## Critique

One of the things I would like to improve is the query to search for posts. Initially, I used a few joins to get the data, but in a real project, I would spend more time on this to make it as optimized as possible.

I would also create a materialized view to save the user's post count. To be updated from time to time. I wouldn't want the user to count the whole table of posts everytime they open their profiles.

In order to scale, the project as is, I would use a serverless option like cloud run. It would scale automatically as the demand increases.
I would also choose a serverless database like Firestore if possible. If not, I would consider a noSql datatable, like mongo ou or a SaaS like AWS DynamoDB.

But I would want to run some tests first, to make sure the cold start is not affecting the user experience.
If yes, I would consider kubernetes ou GCP App engine, to make the application available at all times.
And, it would be necessary to set up the conditions to when the application should be scaled.

At the moment, as the user base increases, the first thing to fail would be the search for posts. 
Some solutions above would mitigate part of the problem. Query optimization would depend on the database.