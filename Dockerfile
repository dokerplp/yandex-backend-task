FROM openjdk:11
ADD target/yandex-backend-task.jar yandex-backend-task.jar
ENTRYPOINT ["java", "-jar","yandex-backend-task.jar"]
EXPOSE 8080