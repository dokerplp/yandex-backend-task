package dokerplp.yandexbackendschool;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@Slf4j
@SpringBootApplication
public class YandexBackendSchoolApplication {

    private static final Logger logger = LoggerFactory.getLogger(YandexBackendSchoolApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(YandexBackendSchoolApplication.class, args);
    }
}
