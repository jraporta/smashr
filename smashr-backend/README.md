# Smashr Microservices Project

### Overview

This is a multi-module Spring Boot microservices project using gRPC and Protobuf for communication. The project structure includes:

### Project Structure

```
smashr/ (parent pom)
│
├── proto-definition/       # Protobuf files and generated sources
├── user/                  # User microservice
├── game-manager/          # Game manager microservice
├── table-manager/         # Table manager microservice
├── run-all.sh             # Run all modules script
└── mvnw                   # Maven wrapper script
```

### Prerequisites
- Java 21
- MongoDB

### Building the Project
From the root folder, run:

```bash
./mvnw clean install
```

This will:
- Compile all modules
- Generate protobuf classes
- Run tests

### Running the Microservices

1. Make sure mongoDB is running.

2. In separate terminals, run the individual microservices:
```bash
./mvnw -pl user spring-boot:run
```

```bash
./mvnw -pl table-manager spring-boot:run
```

```bash
./mvnw -pl game-manager spring-boot:run
```

Alternatively, start all the microservices executing the script:

```bash
./run-all.sh
```

### Notes
- The proto-definition module only contains protobuf definitions and does not run as a service.
- The microservices use gRPC on configured ports; ensure no port conflicts when running multiple modules.

### Contributing
Contributions are welcome! Please open an issue or submit a pull request for any bugs or feature suggestions.

### License
This project is licensed under the MIT License.