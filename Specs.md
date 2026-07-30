# BOOT FORGE

---


## PROBLEM

---
As a java backend developer, I struggle to remember spring boot configuration settings when I am starting a new spring boot project, I will like a tool that can help me generate basic and production ready application.properties or application.yml configuration for quick bootstrapping of my project



## GOAL

---
BootForge is a backend-focused Spring Boot configuration generation service designed to help developers quickly create clean, production-ready configuration.

Instead of manually remembering dozens of Spring Boot properties, BootForge allows you to:



* Generate configuration in application.properties or application.yml format
* Configure database, JPA, logging, server, and actuator settings
* Apply safe, opinionated default



## USER

---
Java backend developer who want to quickly configure their spring boot application



## REQUIREMENTS

---
User should be able to generate configuration for 

* application name.
* profile.
* actuator endpoints.
* database.
* Hikari.
* JPA,
* logging.
* server port.
* context path.


## 🧩 User Stories (WHAT the user wants)

---
### 🎯 Core Functionality

- As a Java backend developer, I want to generate a Spring Boot configuration so that I can quickly bootstrap my application.
- As a user, I want to choose between application.properties and application.yml so that I can match my project preference.

### ⚙️ Configuration Options

- As a user, I want to set the application name so that my service is properly identified.
- As a user, I want to configure the active profile so that I can manage environment-specific behaviour.
- As a user, I want to configure database settings so that my application can connect to a database.
- As a user, I want to configure connection pooling (Hikari) so that my application performs efficiently.
- As a user, I want to configure JPA settings so that I can control ORM behaviour.
- As a user, I want to configure logging so that I can monitor my application.
- As a user, I want to configure actuator endpoints so that I can monitor application health.
- As a user, I want to configure server port and context path so that my application runs on the correct endpoint.


### ⚡ Defaults \& Productivity

- As a user, I want sensible default values so that I don’t have to configure everything manually.



### 📤 Output

- As a user, I want to download or copy the generated configuration so that I can use it in my project.



## 🧠 Use Cases (HOW the system behaves)

---

### 🔵 Use Case 1: Generate Configuration (CORE FLOW)

**Actor:**
User (Java developer)

**Main Flow**

- User selects configuration format:
    - application.properties OR application.yml

- User provides input:
  - application name
  - profile
  - database settings (URL, username, password, driver)
  - JPA settings
  - logging level 
  - actuator settings 
  - server port 
  - context path
  
- User submits request
- System validates input
- System applies default values where input is missing
- System generates configuration
- System returns formatted configuration

***Edge Cases***

- Invalid database URL
- Unsupported format selection
- Missing required fields
- Invalid port number

### 🔵 Use Case 2: Apply Default Configuration

**Actor:**
System

**Main Flow**

- System receives partial configuration input
- System checks missing fields
- System applies predefined defaults:
- default logging level
- default server port (e.g. 8080)
- default actuator exposure
- System merges user input with defaults
- System proceeds to generation

***Edge Cases***

Conflict between user input and defaults

### 🔵 Use Case 3: Generate Properties Format

**Actor:**
System

**Main Flow**

- System receives configuration model
- System maps fields to .properties format
- System builds key-value pairs
- System returns formatted string

<details>
<summary>Example Output</summary>

spring.application.name=bootforge

server.port=8080
</details>

### 🔵 Use Case 4: Generate YAML Format

**Actor:**
System

**Main Flow**

- System receives configuration model
- System maps fields into hierarchical YAML structure
- System formats output correctly
- System returns YAML configuration

<details>
<summary>Example Output</summary>

spring:

  application:

   name: bootforge

</details>

### 🔵 Use Case 5: Export Configuration

**Actor:**
User

**Main Flow**

- User requests generated configuration
- System returns:

  - Copyable text (formatted configuration in the UI)
  - Downloadable file (application.properties or application.yml)

- User uses configuration in project



For a 2–3 week BootForge deployment, focus only on the essentials. Group the work into four stages.

Stage 1 — Production configuration

Implement first:

Create application-dev.yml
Create application-prod.yml
Configure environment variables for:
server port
allowed CORS origins
logging level
Confirm BootForge runs locally with the production profile

Outcome: the same application can run in different environments without changing source code.

Stage 2 — Health, CORS and logging

Implement:

Add Spring Boot Actuator
Expose only:
/actuator/health
/actuator/health/liveness
/actuator/health/readiness
Add controlled CORS configuration
Configure JSON logging for production

You can postpone correlation IDs until after deployment.

Outcome: the cloud platform can check whether BootForge is healthy, and a future frontend can access it safely.

Stage 3 — Docker and local validation

Implement:

Create a multi-stage Dockerfile
Run the application as a non-root user
Add .dockerignore
Create a simple docker-compose.yml
Add a Compose health check
Build and test the container locally

Essential commands:

docker build -t bootforge:local .
docker compose up --build

Verify:

/actuator/health/readiness
/api/v1/configurations

Outcome: BootForge runs locally in approximately the same way it will run in production.

Stage 4 — CI and Render deployment

Implement:

Create one GitHub Actions workflow
Run mvn clean verify
Build the Docker image
Start the image in GitHub Actions
Check the readiness endpoint
Deploy BootForge to Render
Configure Render environment variables
Configure Render health check
Test the public API

For speed, let Render build directly from your GitHub repository and Dockerfile.