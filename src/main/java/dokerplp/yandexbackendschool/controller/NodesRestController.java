package dokerplp.yandexbackendschool.controller;

import dokerplp.yandexbackendschool.exception.NotFoundException;
import dokerplp.yandexbackendschool.model.entity.ShopUnit;
import dokerplp.yandexbackendschool.responses.Response;
import dokerplp.yandexbackendschool.responses.IResponse;
import dokerplp.yandexbackendschool.model.service.ShopUnitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class NodesRestController {
    private final Logger logger = LoggerFactory.getLogger(NodesRestController.class);

    @Autowired
    private ShopUnitService shopUnitService;

    @GetMapping("/nodes/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ShopUnit nodes(@PathVariable UUID id) {
        ShopUnit unit = shopUnitService.findById(id);
        if (unit == null) throw new NotFoundException();
        return unit;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public IResponse handleNotFoundException(NotFoundException e) {
        return Response.NOT_FOUND.getResponse();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public IResponse handleBadRequestException(Exception e) {
        logger.error(e.getMessage(), e);
        return Response.BAD_REQUEST.getResponse();
    }
}
