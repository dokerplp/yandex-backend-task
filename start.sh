sudo iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 8080
mvn clean install -DskipTests
sudo docker build -t yandex-backend-task.jar .
sudo docker-compose up