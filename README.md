# API Challenge

Design and create a RESTful API that could be used to manage a list of dog images. 

# Requirements

The operations we expect to see would be:

* List all of the available dog pictures grouped by breed
* List all of the available dog pictures of a particular breed
* Vote up and down a dog picture
* The details associated with a dog picture

The information the Dog Breed App needs to function is:

* A URL to a dog picture
* The number of votes a picture has received
* The dog's breed
* Any other information required to identify a specific dog

The Dog Breed App expects the response to be sorted by the number of votes a picture has received.

The API responses must be in JSON.

## Additional Voting Requirements

Each client is allowed to vote once for any particular dog picture.

# Setup

We provide you with a starter Spring Boot project. The project is already configured go use Spring MVC and talks to an 
in memory HSQLDB to store the results. Jackson is already included to provide JSON serialization and deserialization.

The Spring Boot Starter Test module is included to facilitate both unit and integration tests.

Please follow these steps:

* Clone api_challenge to your local workstation.
* Follow the instructions on the home page of your private repository to initialize it on your local workstation.
* Copy the code from the local copy of api_challenge to initialize your private repository.
* Push the copied code to the remote of your private repository.
* Push your edits to your private repository as you go

## Dependencies

- Maven
- Java 8

## Checking Out the Project

```
$ git clone https://gitlab.com/disney-techfirefly/api_challenge.git
```

## Building

```
$ mvn package
```

## Running

You can run the app through Maven:

```
$ mvn spring-boot:run
```

or you can run the jar file from the build:

```
$ java -jar target/api_interview-0.1.0.jar
```

# Build, Deployment and Running

While this falls outside of the challenge, please consider the following as you get ready to discuss your solution with the team:

* How would you package this for deployment?
* How and where would you deploy this app?
* How can you tell that the app is up and running?
* How would you configure the app as it goes from dev, to qa and finally to production?
* How would you insulate the app from a downstream API if it had one.

# Submitting the Project

You will also receive an invitation to your own private repository on gitlab.com. Push your code to that repository as you go. Instructions are provided by gitlab.com on how to push your code to a new repo.

# Duration

In our tests, we found it to take us about 4 to 6 hours to build, however, we encourage you to spend enough time to polish/document/test your app. Commit times will be considered when ranking your solutions but meeting all requirements is paramount.  Please email us when all requirements are met.
The timestamp of your last commit will be used to gage how long you worked on this project.