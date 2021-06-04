# Micro-service

Microservices - also known as the microservice architecture - is an architectural style that structures an application as a collection of services that are

- Highly maintainable and testable
- Loosely coupled
- Independently deployable
- Organized around business capabilities
- Owned by a small team

The microservice architecture enables the rapid, frequent and reliable delivery of large, complex applications. It also enables an organization to evolve its technology stack.

## Features

In the demonstration, we have the microservice architecture with 5 components
- Spring Cloud Service Discovery: [Eureka](https://www.baeldung.com/spring-cloud-netflix-eureka)
- Spring Cloud Gateway
- Authentication Service (Spring web): store sensitive user information (e.g: username, password, role,...)
- Customer Service (Spring web)
- Order Service (Spring web)
- Shipper Service (Spring web)

We're using [gRPC](https://grpc.io/) to communicate between each service. 

![GitHub Logo](/images/MicroService.png)

## Preparation

- Terminal or CMD
- MySQL Workbench
- [docker](https://docs.docker.com/engine/install/)
- [maven](https://maven.apache.org/install.html)
- [Open JDK 11](https://openjdk.java.net/install/)

## Installation

1.  Connect to mysql database in local with account (root/root)
2.  Run 
```bash
-- Clear data
DROP SCHEMA IF EXISTS `auth`;
DROP SCHEMA IF EXISTS `customer`;
DROP SCHEMA IF EXISTS `order`;
DROP SCHEMA IF EXISTS `shipping`;

-- Create schema
CREATE SCHEMA `auth`;
CREATE SCHEMA `customer`;
CREATE SCHEMA `order`;
CREATE SCHEMA `shipping`;

```

3. Run the services in local

```bash
cd Services

mvn clean package -DskipTests

docker network create internal

docker-compose up --build
```
