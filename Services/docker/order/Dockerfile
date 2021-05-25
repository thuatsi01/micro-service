FROM maven:3.8-openjdk-11-slim
COPY /OrderService/target/order-service.jar /order/
ENTRYPOINT java \
 -jar /order/order-service.jar