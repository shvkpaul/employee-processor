# Employee Processor

# swagger : http://localhost:8091/swagger-ui/index.html

## Overview
The Employee Processor is a Spring Boot application that provides RESTful APIs to manage employee data. It supports operations such as creating, updating, retrieving, and deleting employee records.

## Prerequisites
- Java 11 or higher
- Maven 3.6.0 or higher
- Docker (optional, for running the application in a container)

## Setup Instructions

### Clone the Repository
```sh
git clone https://github.com/shvkpaul/employee-processor
```

Build the Project : mvn clean install

Run the Application : mvn spring-boot:run

Running Tests
To run the unit and integration tests, use the following command: mvn test

Steps to build and run the Docker image:

Build the Docker image:  
    docker build -t employee-processor .
Run the Docker container:
    docker run -p 8080:8080 employee-processor

Additionally added postman collection, look for employee-processor from the root directory of the collection.
