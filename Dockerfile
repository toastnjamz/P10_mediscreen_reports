
FROM adoptopenjdk/openjdk11:alpine-jre
COPY build/libs/report-0.0.1-SNAPSHOT.jar mediscreen-reports.jar
ENTRYPOINT ["java", "-jar", "mediscreen-reports.jar"]