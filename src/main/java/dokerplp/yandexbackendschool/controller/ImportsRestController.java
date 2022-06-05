package dokerplp.yandexbackendschool.controller;

import dokerplp.yandexbackendschool.model.dto.*;
import dokerplp.yandexbackendschool.model.httpDto.HttpDto;
import dokerplp.yandexbackendschool.model.httpDto.Ok;
import dokerplp.yandexbackendschool.model.httpDto.Response;
import dokerplp.yandexbackendschool.model.service.ShopUnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.web.bind.annotation.*;

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
    public Response handleBadRequestException(Exception e) {
        logger.error(e.getMessage(), e);
        return HttpDto.BAD_REQUEST.getResponse();
    }
}
