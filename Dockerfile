FROM openjdk:17-slim
LABEL authors="ShchinVV"
COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]