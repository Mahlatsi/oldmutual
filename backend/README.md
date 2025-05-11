# Flag Explorer Backend

This is the backend service for the Flag Explorer application, built with Spring Boot and Kotlin.

## Prerequisites

- Java 17 or higher
- Gradle 8.0 or higher

## Building the Project

To build the project, run:

```bash
./gradlew build
```

## Running the Application

To run the application, use:

```bash
./gradlew bootRun
```

The application will start on port 8080.

## API Documentation

Once the application is running, you can access the Swagger UI documentation at:

```
http://localhost:8080/swagger-ui.html
```

## API Endpoints

### Get All Countries
```
GET /countries
```

### Get Country Details
```
GET /countries/{name}
```

## Running Tests

To run the tests:

```bash
./gradlew test
```

## Project Structure

- `src/main/kotlin/com/flagexplorer/`
  - `controller/` - REST controllers
  - `service/` - Business logic
  - `model/` - Data models
  - `config/` - Configuration classes
- `src/test/kotlin/com/flagexplorer/` - Test classes 