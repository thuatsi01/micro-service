FROM maven:3.8-openjdk-11-slim
COPY /EurekaService/target/eureka-service.jar /eureka/
ENTRYPOINT java \
 -jar /eureka/eureka-service.jar