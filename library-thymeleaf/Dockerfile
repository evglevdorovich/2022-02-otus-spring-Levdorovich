FROM maven:3.8.6-jdk-11 AS MAVEN_BUILD
ENV PROJECT_DIR=/project/app
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY ./pom.xml $PROJECT_DIR
RUN mvn dependency:resolve
COPY ./src $PROJECT_DIR/src
RUN mvn install
FROM openjdk:11.0.15-jdk
ENV PROJECT_DIR=/project/app
RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR
COPY --from=MAVEN_BUILD $PROJECT_DIR/target/library-thymeleaf* $PROJECT_DIR/
EXPOSE 8080
CMD ["java", "-jar", "/project/app/library-thymeleaf.jar"]