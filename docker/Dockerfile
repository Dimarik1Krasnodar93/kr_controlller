FROM maven:3.8-openjdk-17 as maven
WORKDIR kr_controlller
COPY . .
RUN mvn install

FROM openjdk:17.0.2-jdk
WORKDIR kr_controlller
COPY --from=maven /controller/target/controller.jar demo.jar
CMD java -jar demo.jar