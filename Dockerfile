FROM openjdk:17-jdk-slim

COPY target/todolist-0.0.1-SNAPSHOT.jar todolist-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java","-jar","/todolist-0.0.1-SNAPSHOT.jar","-web -webAllowOthers -tcp -tcpAllowOthers -browser"]

