package dokerplp.yandexbackendschool.controller;

import dokerplp.yandexbackendschool.model.dto.*;
import dokerplp.yandexbackendschool.model.dto.Error;
import dokerplp.yandexbackendschool.model.service.ShopUnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
public class ImportsRestController {
    private final Logger logger = LoggerFactory.getLogger(ImportsRestController.class);
    @Autowired
    private ShopUnitService shopUnitService;

    @PostMapping("/imports")
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> imports(@RequestBody ShopUnitImportRequest shopUnitImportRequest) {
        try {
            shopUnitService.saveAll(shopUnitImportRequest.getItems().stream()
                            .map((f) -> f.toShopUnit(shopUnitImportRequest.getUpdateDate()))
                            .collect(Collectors.toList()));
            return new ResponseEntity<>(new Ok(), HttpStatus.OK);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return new ResponseEntity<>(new Error(HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }
}
