package dokerplp.yandexbackendschool.controller;

import dokerplp.yandexbackendschool.dto.ShopUnitImportRequest;
import dokerplp.yandexbackendschool.model.entity.ShopUnit;
import dokerplp.yandexbackendschool.model.service.ShopUnitService;
import dokerplp.yandexbackendschool.responses.IResponse;
import dokerplp.yandexbackendschool.responses.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ImportsRestController {
    private final Logger logger = LoggerFactory.getLogger(ImportsRestController.class);
    @Autowired
    private ShopUnitService shopUnitService;

    @PostMapping("/imports")
    @ResponseStatus(HttpStatus.OK)
    public IResponse imports(@RequestBody ShopUnitImportRequest shopUnitImportRequest) {
        LocalDateTime date = shopUnitImportRequest.getUpdateDate();
        List<ShopUnit> items = shopUnitImportRequest.getItems().stream()
                .map((e) -> e.toShopUnit(date))
                .collect(Collectors.toList());

        shopUnitService.saveAll(items, date);

        return Response.OK.getResponse();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public IResponse handleBadRequestException(Exception e) {
        logger.error(e.getMessage(), e);
        return Response.BAD_REQUEST.getResponse();
    }
}
