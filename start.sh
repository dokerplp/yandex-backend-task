mvn clean install -DskipTests
sudo docker build -t yandex-backend-task.jar .
sudo docker-compose up