FROM maven:3.8-openjdk-11-slim
COPY /ShippingService/target/shipping-service.jar /shipping/
ENTRYPOINT java \
 -jar /shipping/shipping-service.jar