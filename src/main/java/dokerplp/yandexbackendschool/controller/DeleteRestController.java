package dokerplp.yandexbackendschool.controller;

import dokerplp.yandexbackendschool.model.httpDto.HttpDto;
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

import java.util.UUID;

@RestController
public class DeleteRestController {

    private final Logger logger = LoggerFactory.getLogger(DeleteRestController.class);

    @Autowired
    private ShopUnitService shopUnitService;

    @GetMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Response deleteById(@PathVariable UUID id) {
        if (shopUnitService.deleteById(id) == null)
            return HttpDto.NOT_FOUND.getResponse();
        return HttpDto.OK.getResponse();
    }

//    @ExceptionHandler({
//            DataIntegrityViolationException.class,
//            HttpMessageNotReadableException.class,
//            JpaSystemException.class
//    })
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Response handleBadRequestException(Exception e) {
        logger.error(e.getMessage(), e);
        return HttpDto.BAD_REQUEST.getResponse();
    }
}
