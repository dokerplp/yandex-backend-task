sudo mv yandex.service /etc/systemd/system/
sudo systemctl daemon-reload
sudo systemctl stop yandex
sudo systemctl enable yandex
sudo systemctl start yandex