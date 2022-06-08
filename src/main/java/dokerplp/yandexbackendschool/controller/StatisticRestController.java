package dokerplp.yandexbackendschool.controller;

import dokerplp.yandexbackendschool.dto.ShopUnitStatisticResponse;
import dokerplp.yandexbackendschool.model.service.ShopUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
public class StatisticRestController {

    @Autowired
    private ShopUnitService shopUnitService;

    @GetMapping("/node/{id}/statistic")
    @ResponseStatus(HttpStatus.OK)
    public ShopUnitStatisticResponse statistic(
            @PathVariable UUID id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateEnd
    ) {
        return new ShopUnitStatisticResponse();
    }

}
