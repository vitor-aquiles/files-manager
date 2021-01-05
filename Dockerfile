FROM openjdk:8
VOLUME /tmp
COPY target /target
ENTRYPOINT ["java","-jar","/target/FilesManager-1.0-SNAPSHOT.jar"]