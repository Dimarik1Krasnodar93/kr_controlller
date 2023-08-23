FROM maven:3.8-openjdk-17 as maven
WORKDIR kr_controlller
COPY . .
RUN mvn install -DskipTests

FROM openjdk:17.0.2-jdk
WORKDIR kr_controlller
COPY --from=maven /kr_controlller/target/controller.jar demo.jar
EXPOSE 8080
CMD java -jar controller.jar