package dokerplp.yandexbackendschool.controller;

import dokerplp.yandexbackendschool.dto.ShopUnitStatisticResponse;
import dokerplp.yandexbackendschool.dto.ShopUnitStatisticUnit;
import dokerplp.yandexbackendschool.model.service.ShopUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class SalesRestController {

    @Autowired
    private ShopUnitService shopUnitService;

    @GetMapping("/sales")
    @ResponseStatus(HttpStatus.OK)
    public ShopUnitStatisticResponse sales (
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date
    ) {
        List<ShopUnitStatisticUnit> statisticUnits = shopUnitService.findAllSalesByDate(date).stream()
                .map(ShopUnitStatisticUnit::fromShopUnit)
                .collect(Collectors.toList());
        return new ShopUnitStatisticResponse(statisticUnits);
    }
}
