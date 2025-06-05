# tibco-jms-spring-example
Example of using Tibco as a JMS bus for sending a message with Spring Boot 3.5.0 and Java 21

For the application to work you need to have the following:
- A bus running with defined user and queue

## Project Configuration
This project uses system scope dependencies for the Tibco JMS libraries and other required JARs. The dependencies are included in the `/lib` directory and configured in the `pom.xml` file.

## Running a bus
Inside the tibco installation folder usually at C:\tibco\ems\8.5\samples\config

You will need to run the following commands:

```
mkdir datastore

tibemsd -config tibemsd.conf
```

And in another command line you need to run the following commands:

```
tibemsadmin -server "tcp://localhost:7222"

create user user password=password

create queue test.queue
```

## Building and Running the Application
To build and run the application, use the following commands:

```
mvn clean package
java -jar target/tibco-jms-spring-example-0.0.1-SNAPSHOT.jar
```

## Testing the Application
To test the application, you can send a POST request to the `/api/message` endpoint with a JSON payload:

```
curl -X POST -H "Content-Type: application/json" -d "Hello, World!" http://localhost:8080/api/message
```