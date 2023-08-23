FROM maven:3.8-openjdk-17 as maven
WORKDIR kr_controller
COPY . .
RUN mvn install

FROM openjdk:17.0.2-jdk
WORKDIR kr_controller
COPY --from=maven /kr_controller/target/controller.jar demo.jar
CMD java -jar demo.jar