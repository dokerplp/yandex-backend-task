sudo iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-port 8080
sudo mv yandex.service /etc/systemd/system/
sudo systemctl enable yandex
sudo systemctl start yandex