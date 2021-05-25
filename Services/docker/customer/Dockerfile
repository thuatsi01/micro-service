FROM maven:3.8-openjdk-11-slim
COPY /CustomerService/target/customer-service.jar /customer/
ENTRYPOINT java \
 -jar /customer/customer-service.jar