FROM eclipse-temurin:21.0.2_13-jdk

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} /home/java/app.jar

ENTRYPOINT ["java","-jar","/home/java/app.jar"]
