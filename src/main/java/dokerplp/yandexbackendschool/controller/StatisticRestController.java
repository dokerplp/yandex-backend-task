package dokerplp.yandexbackendschool.controller;

import dokerplp.yandexbackendschool.dto.ShopUnitStatisticResponse;
import dokerplp.yandexbackendschool.dto.ShopUnitStatisticUnit;
import dokerplp.yandexbackendschool.exception.NotFoundException;
import dokerplp.yandexbackendschool.model.repository.ShopUnitRepository;
import dokerplp.yandexbackendschool.model.service.HistoryService;
import dokerplp.yandexbackendschool.responses.IResponse;
import dokerplp.yandexbackendschool.responses.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class StatisticRestController {

    private final Logger logger = LoggerFactory.getLogger(StatisticRestController.class);

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ShopUnitRepository shopUnitRepository;

    @GetMapping("/node/{id}/statistic")
    @ResponseStatus(HttpStatus.OK)
    public ShopUnitStatisticResponse statistic(
            @PathVariable UUID id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateStart,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateEnd
    ) {
        if (shopUnitRepository.findById(id).isEmpty())
            throw new NotFoundException();


        ShopUnitStatisticResponse susr = new ShopUnitStatisticResponse();
        List<ShopUnitStatisticUnit> items = historyService.getStatistic(id, dateStart, dateEnd).stream()
                .map(ShopUnitStatisticUnit::fromHistory)
                .sorted(Comparator.comparing(ShopUnitStatisticUnit::getId))
                .collect(Collectors.toList());
        susr.setItems(items);

        return susr;
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
