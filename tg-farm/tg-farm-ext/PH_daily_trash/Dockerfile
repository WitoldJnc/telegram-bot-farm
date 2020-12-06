FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/PH_daily_trash.jar
ADD ${JAR_FILE} PH_daily_trash.jar
ENTRYPOINT ["java","-jar","/PH_daily_trash.jar"]