package dokerplp.yandexbackendschool.controller;

import dokerplp.yandexbackendschool.model.dto.*;
import dokerplp.yandexbackendschool.model.dto.Error;
import dokerplp.yandexbackendschool.model.entity.ShopUnit;
import dokerplp.yandexbackendschool.model.service.ShopUnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class ImportsRestController {
    private final Logger logger = LoggerFactory.getLogger(ImportsRestController.class);
    @Autowired
    private ShopUnitService shopUnitService;

    @PostMapping("/imports")
    @ResponseStatus(HttpStatus.OK)
    public Response imports(@RequestBody ShopUnitImportRequest shopUnitImportRequest) {
        shopUnitService.saveAll(shopUnitImportRequest.getItems().stream()
                .map((f) -> f.toShopUnit(shopUnitImportRequest.getUpdateDate()))
                .collect(Collectors.toList()));
        return new Ok();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleNoSuchElementFoundException(Exception e) {
        logger.error(e.getMessage(), e);
        return new Error(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public ShopUnit getFooById(@RequestParam UUID id) {
        return shopUnitService.findById(id);
    }
}
