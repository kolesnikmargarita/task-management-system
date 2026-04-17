FROM openjdk:21-ea
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]