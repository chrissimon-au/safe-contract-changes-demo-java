# Safe Contract Changes Demo

This repository contains a sample full stack application to illustrate:

1. the risks when changing contracts between independently deployed components, and;
2. patterns for doing so safely

## Application Overview

The application is a simple illustration of a common scenario - capturing, storing and retrieving data.

In this case, we are capturing a `name`, storing it (which allocates a new UUID) and then retriving it by its UUID.

### Architecture

The application is made of three components:

* [Next.js](https://nextjs.org/) Single Page App
* Java Backend App
    * [Spring Boot](https://spring.io/projects/spring-boot) REST API
    * [Hibernate](https://hibernate.org/) Persistence
    * [Liquibase](https://www.liquibase.com/) Schema Management
* [CDK Infrastructure](https://docs.aws.amazon.com/cdk/v2/guide/home.html)
    * Cloudfront -> S3 to host the SPA
    * ECS Fargate to host the Java Service
    * RDS Aurora Serverless v2 to host the database

### Contracts

A contract is a formal definition of how two components should interact.  With software contracts, we use the terms `client` and `server` in a generic way:

1. `client` refers to the component initiating the interaction
1. `server` refers to the component responding to the interaction

There are a number of contracts in the application between components that might be affected by the timing of the deployments of the `client` and `server`:

1. API to add a name (POST):
    a. the SPA is the `client`, the Java app is the `server`
1. Writing the name to the database:
    a. the Java app is the `client`, the DB is the `server` (executing queries provided by the Java app)
1. Reading the name from the database:
    a. the Java app is the `client`, the DB is the `server` (executing queries provided by the Java app)
1. API to get a name (GET):
    a. the SPA is the `client`, the Java app is the `server`

# Demo

These demos illustrate different ways of introducing a new feature - instead of capturing a single name, we now need to capture a 'full name' as two separate fields: `firstName` and `lastName`.

## The WRONG Way

To demonstrate the wrong way, first checkout the [tag 1 commit](https://github.com/chrissimon-au/safe-contract-changes-demo-java/commit/1) which contains a baseline version of the application before the change and deploy it:

```
git checkout 1
cd infra
cdk deploy
```

(This assumes you have AWS credentials setup in your environment and a number of other common dependencies, including `cdk` installed globally and `docker`.)

Once deployed, the 'base' version of the application will be available to you in your AWS account.

Next, checkout the [single commit change (tag 2)](https://github.com/chrissimon-au/safe-contract-changes-demo-java/commit/2) and start a test loop:

```
git checkout 2
cd tests/e2e
npm run test:aws:loop
```

This will start a playwright test in a loop that captures 10 names and then repeats.

> **NOTE:** The Tests are written to operate like a human - they are flexible and will pass with either the 'single field' version or the 'two fields' versions of the app. A human will do their best to make use of the screen presented to them - so do these tests.

If you run the tests against the first version of the app, they will pass.  And if you run them against the second version of the app, they will pass. The interesting thing is: what happens if you run them while the deployment is in mid-flight? This attempts to demonstrate the the experience of a user who lands on your application while the deployment is underway.

To find out, while the test loop is running, in a separate terminal, re-deploy using (from the repo root):

```
cd infra
cdk deploy
```

After a few minutes, the tests will failing.

If you re-start them immediately, they will still fail.

However, if you wait long enough, and re-start the tests, they will start passing.

### The reason for the failure

There are a few possible reasons for the failures you've seen:

1. The SPA completed deployment before the server, and was passing down two fields and expecting two back, to an app that was still expecting 1 field and returning 2
2. The server had completed deployment and updated the database schema.  The SPA had not yet completed deployment, and was randomly interacting with an old version of the server that hadn't been 'rolled over' yet, and the old version of the server was unable to make use of the new database schema.

Eventually, once all deployments are completed and the versions are aligned, the tests will pass again.