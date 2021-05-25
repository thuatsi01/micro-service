FROM maven:3.8-openjdk-11-slim
COPY /AuthService/target/auth-service.jar /auth/
ENTRYPOINT java \
 -jar /auth/auth-service.jar