# Blogging Platform API

A robust REST API for a blogging platform built with Spring Boot 3.x.

## Prerequisites

Ensure the following tools/software are installed and configured:

1. **JDK 21+**
   - Install JDK version 21 or later
   - Set the `JAVA_HOME` environment variable to the JDK installation directory

2. **Gradle 7+**
   - Install Gradle (version 7 or later) and verify it is accessible:
     ```sh
     gradle --version
     ```

3. **Docker**
   - Install Docker and confirm it is running:
     ```sh
     docker --version
     ```

## Getting Started

### 1. Clone the Repository
```sh
git clone <repository-url>
cd <project-folder>
```

### 2. Build and Run the Application
Execute the following command to clean and run the application:
```sh
./gradlew clean bootRun
```

**What this does:**
- Builds the application
- Automatically initializes and configures a MySQL database container using Docker

### 3. Access the Application

#### API Endpoints
The application will be available at:
```
http://localhost:8080/v1/blogging-platform
```

#### API Documentation
The API documentation is available in two formats:

1. **Swagger UI** (Interactive Documentation)
   ```
   http://localhost:8080/v1/blogging-platform/swagger-ui.html
   ```
   - Provides an interactive interface to explore and test the API
   - Includes detailed request/response schemas
   - Allows direct API testing from the browser

2. **OpenAPI JSON** (Raw Specification)
   ```
   http://localhost:8080/v1/blogging-platform/v3/api-docs
   ```
   - Provides the raw OpenAPI specification in JSON format
   - Useful for generating client code or importing into API tools

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── org/deimos/projects/bloggingplatformapi/
│   │       ├── controller/     # REST controllers
│   │       ├── service/        # Business logic
│   │       ├── repository/     # Data access layer
│   │       ├── model/          # Data models and DTOs
│   │       └── configuration/  # Application configuration
│   └── resources/
│       ├── application.yml     # Main configuration
│       └── application-*.yml   # Environment-specific configs
└── test/                      # Test files
```

## Features

- RESTful API design
- OpenAPI documentation
- MySQL database integration
- Docker containerization
- Spring Security integration
- Actuator endpoints for monitoring
- Comprehensive test coverage

## Development Notes

- Local Database is only created in dev environment
- Docker daemon must be active for database container
- No manual Docker commands required

## Troubleshooting

If issues arise:
1. Confirm Docker is correctly set up and running
2. Check application logs for specific error messages
3. Verify database connection settings in `application-dev.yml`
4. Ensure all prerequisites are properly installed and configured

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.