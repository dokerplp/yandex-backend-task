package dokerplp.yandexbackendschool.model.service;

import dokerplp.yandexbackendschool.exception.BadRequestException;
import dokerplp.yandexbackendschool.model.entity.History;
import dokerplp.yandexbackendschool.model.entity.ShopUnit;
import dokerplp.yandexbackendschool.model.entity.ShopUnitType;
import dokerplp.yandexbackendschool.model.repository.ShopUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShopUnitService {
    @Autowired
    private ShopUnitRepository shopUnitRepository;

    @Autowired
    private HistoryService historyService;

    private Set<ShopUnit> getAllParents(ShopUnit unit) {
        Set<ShopUnit> units = new HashSet<>();
        if (unit.getParentId() == null) return units;
        Optional<ShopUnit> parent = shopUnitRepository.findById(unit.getParentId());
        while (parent.isPresent()) {
            units.add(parent.get());
            if (parent.get().getParentId() == null) break;
            parent = shopUnitRepository.findById(parent.get().getParentId());
        }
        return units;
    }

    public void saveAll(List<ShopUnit> items, LocalDateTime dateTime) {
        shopUnitRepository.saveAll(items);

        Set<ShopUnit> units = new HashSet<>(items);
        if (units.size() != items.size()) throw new BadRequestException();

        for (ShopUnit unit : items)
            units.addAll(getAllParents(unit));

        units.forEach((e) -> e.setDate(dateTime));
        shopUnitRepository.saveAll(units);

        Set<History> histories = units.stream().map(History::new).collect(Collectors.toSet());
        historyService.updateHistory(histories);
    }

    public ShopUnit findById(UUID id) {
        Optional<ShopUnit> optional = shopUnitRepository.findById(id);
        if (optional.isEmpty()) return null;
        ShopUnit unit = optional.get();

        if (unit.getType() == ShopUnitType.OFFER) return unit;

        List<ShopUnit> children = shopUnitRepository.findAllChildrenById(id).stream()
                .map((e) -> findById(e.getId()))
                .collect(Collectors.toList());

        unit.setPrice(historyService.getLatestPrice(id));
        unit.setChildren(children.isEmpty() ? null : children);
        return unit;
    }

    public ShopUnit deleteById(UUID id) {
        ShopUnit del = delete(id);
        if (del == null) return null;

        Set<ShopUnit> units = getAllParents(del);

        LocalDateTime now = LocalDateTime.now();

        Set<History> histories = units.stream()
                .map(History::new)
                .peek((e) -> e.setDate(now))
                .collect(Collectors.toSet());

        historyService.updateHistory(histories);

        return del;
    }

    private ShopUnit delete(UUID id) {
        Optional<ShopUnit> optional = shopUnitRepository.findById(id);
        if (optional.isEmpty()) return null;
        ShopUnit unit = optional.get();

        List<ShopUnit> children = shopUnitRepository.findAllChildrenById(id);
        children.forEach((e) -> delete(e.getId()));

        historyService.deleteAllByShopUnitId(id);
        shopUnitRepository.deleteById(unit.getId());
        return unit;
    }


    public List<ShopUnit> findAllSalesByDate(LocalDateTime end) {
        return shopUnitRepository.findAllSalesByDate(end.minusDays(1), end);
    }
}
