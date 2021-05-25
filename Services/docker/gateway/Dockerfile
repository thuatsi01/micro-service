FROM maven:3.8-openjdk-11-slim
COPY /ApiGatewayService/target/gateway-service.jar /gateway/
ENTRYPOINT java \
 -jar /gateway/gateway-service.jar