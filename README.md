# BootForge

BootForge is a Spring Boot configuration generator that creates ready-to-use `application.properties` and `application.yml` configuration data from structured configuration options.

Built as a production-oriented Java backend project, BootForge demonstrates REST API design, input validation, automated testing, containerisation, CI and cloud deployment.

## Live Application

BootForge is deployed on Render:

[Try BootForge](https://bootforge.onrender.com)

## Engineering Highlights

- Layered Spring Boot architecture with clear separation between API, service, mapping, domain, and formatting responsibilities
- Strategy-based formatting supporting both YAML and `.properties` output
- Jakarta Bean Validation with centralized API exception handling
- Automated unit, controller, formatter, and integration testing
- Multi-stage Docker build with container health monitoring
- Spring Boot Actuator readiness and liveness probes
- GitHub Actions CI covering Maven verification, Docker image build, container startup, readiness validation, and API smoke testing
- Environment-driven configuration for development and production
- Cloud deployment on Render

## What It Generates

BootForge can generate configuration for:

- application name and active profile
- server port and context path
- datasource URL, username, and password
- PostgreSQL or MySQL database settings
- JPA and Hibernate settings
- Hikari connection pool settings
- logging levels
- actuator endpoint exposure and health detail visibility

## Tech Stack

- **Backend:** Java 21, Spring Boot 4, Spring Web MVC
- **Validation:** Jakarta Bean Validation
- **Configuration:** SnakeYAML
- **Testing:** JUnit 5, Mockito, MockMvc
- **Build:** Maven
- **Containerisation:** Docker, Docker Compose
- **CI:** GitHub Actions
- **Observability:** Spring Boot Actuator
- **Deployment:** Render

## Architecture

BootForge uses a layered architecture that separates HTTP concerns, application orchestration, domain configuration, object mapping, and output formatting.

The request flow is:

`HTTP Request → Controller → Service → Mapper → Domain Model → Formatter → Generated Configuration`

The formatter abstraction allows the service layer to generate different output formats without coupling configuration generation to YAML or `.properties` formatting.

```text
src/main/java/com/jackalcode/BootForge
├── controller   # REST API endpoints
├── domain       # Core configuration models and enums
├── dto          # Request payload records and validation
├── exception    # API error responses and global exception handling
├── formatter    # Properties and YAML output formatters
├── mapper       # DTO-to-domain mapping
└── service      # Configuration generation orchestration
```

## API

BootForge exposes a REST API for generating Spring Boot configuration from a JSON request.

### Endpoint

`POST /api/v1/configurations/generate`

The request defines the required configuration sections and desired output format. The API validates the input, maps it to the internal configuration model, and returns the generated YAML or `.properties` content.

### Example Request

```json
{
  "applicationConfigRequest": {
    "applicationName": "orders-service",
    "activeProfile": "dev"
  },
  "serverConfigRequest": {
    "port": 8080,
    "contextPath": "/api"
  },
  "databaseConfigRequest": {
    "databaseType": "POSTGRESQL",
    "username": "postgres",
    "password": "password",
    "host": "localhost",
    "databaseName": "orders",
    "port": 5432
  },
  "jpaConfigRequest": {
    "ddlAuto": "NONE",
    "showSql": false,
    "openInView": false
  },
  "hikariConfigRequest": {
    "maximumPoolSize": 10,
    "minimumIdle": 2,
    "connectionTimeout": 30000
  },
  "loggingConfigRequest": {
    "rootLevel": "INFO",
    "springLevel": "INFO"
  },
  "actuatorConfigRequest": {
    "exposedEndpoints": "health,info",
    "showHealthDetails": "NEVER"
  },
  "outputFormat": "YAML"
}
```

### Example Response

```yaml
spring:
  application:
    name: orders-service
  profiles:
    active: dev
  datasource:
    url: jdbc:postgresql://localhost:5432/orders
    username: postgres
    password: password
    hikari:
      maximum-pool-size: 10
      minimum-idle: 2
      connection-timeout: 30000
```

## Supported Configuration

BootForge currently supports:

- **Output formats:** `PROPERTIES`, `YAML`
- **Databases:** `POSTGRESQL`, `MYSQL`
- **JPA DDL modes:** `NONE`, `CREATE`, `UPDATE`, `VALIDATE`
- **Log levels:** `TRACE`, `DEBUG`, `INFO`, `WARN`, `ERROR`

## Defaults

BootForge applies defaults when optional fields are not provided.

| Setting | Default |
| --- | --- |
| Active profile | `default` |
| Server port | `8080` |
| Context path | `/` |
| Database host | `localhost` |
| Database name | `app_db` |
| PostgreSQL port | `5432` |
| MySQL port | `3306` |
| JPA DDL auto | `NONE` |
| JPA show SQL | `false` |
| JPA open-in-view | `false` |
| Hikari maximum pool size | `10` |
| Hikari minimum idle | `2` |
| Hikari connection timeout | `30000` |
| Root log level | `INFO` |
| Spring log level | `INFO` |
| Actuator exposed endpoints | `health,info` |
| Health details | `NEVER` |

## Validation

BootForge validates incoming requests using Jakarta Bean Validation before configuration generation.

Required configuration includes:

- application configuration
- server configuration
- database configuration
- output format
- database type, username, and password

Port values must be between `1` and `65535`.

Invalid input returns `400 Bad Request` with a structured error response handled through centralized exception handling.

## Getting Started

### Prerequisites

- Java 21
- Maven, or use the included Maven Wrapper

### Run Locally

macOS/Linux:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

The application starts on port `8080` by default.

### Application Profiles

BootForge provides separate configuration for development and production environments:

- `application-dev.properties`
- `application-prod.properties`

To run using the production profile:

macOS/Linux:

```bash
SPRING_PROFILES_ACTIVE=prod ./mvnw spring-boot:run
```

On Windows PowerShell:

```powershell
$env:SPRING_PROFILES_ACTIVE = "prod"
.\mvnw.cmd spring-boot:run
```

## Health Checks

Actuator is enabled for health monitoring.

Useful endpoints:

```http
GET /actuator/health
GET /actuator/health/liveness
GET /actuator/health/readiness
```

## Docker

BootForge can be built and run as a Docker container.

### Build the Image

```bash
docker build -t bootforge:local .
```

### Run with Docker Compose

```bash
docker compose up --build
```

Docker Compose builds the application image, starts the BootForge service, and exposes the application on port `8000`.

Once running, the API is available at:

```text
http://localhost:8000/api/v1/configurations/generate
```

Stop the application with:

```bash
docker compose down
```

## Testing

BootForge includes automated tests across the controller, service, mapping, formatting, and application integration layers.

Run the complete test suite with:

```bash
./mvnw clean verify
```

Windows:

```powershell
.\mvnw.cmd clean verify
```

The test suite covers:

- request validation and invalid input handling
- controller behaviour and HTTP responses
- DTO-to-domain configuration mapping
- default configuration values
- service orchestration
- `.properties` generation
- YAML structure and generation
- application integration flow

## CI

The GitHub Actions workflow runs on pushes and pull requests to `main`.

It performs:

- Java 21 setup
- Maven build and verification
- Docker image build
- container startup
- readiness check
- API smoke test

## License

No license has been specified yet.
