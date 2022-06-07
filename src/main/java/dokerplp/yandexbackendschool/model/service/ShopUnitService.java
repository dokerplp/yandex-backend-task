package dokerplp.yandexbackendschool.model.service;

import dokerplp.yandexbackendschool.dto.ShopUnitImportRequest;
import dokerplp.yandexbackendschool.exception.BadRequestException;
import dokerplp.yandexbackendschool.model.entity.ShopUnit;
import dokerplp.yandexbackendschool.model.entity.ShopUnitType;
import dokerplp.yandexbackendschool.model.repository.ShopUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShopUnitService {
    @Autowired
    private ShopUnitRepository shopUnitRepository;

    public void saveAll(List<ShopUnit> items, LocalDateTime dateTime) {
        for (ShopUnit unit : items) {
            if (unit.getParentId() == null) continue;
            Optional<ShopUnit> parent = shopUnitRepository.findById(unit.getParentId());
            if (parent.isPresent() && parent.get().getType() == ShopUnitType.OFFER)
                throw new BadRequestException();
            shopUnitRepository.save(unit);
        }
        items.forEach((u) -> updateDate(u, dateTime));
    }

    private void updateDate(ShopUnit item, LocalDateTime dateTime) {
        item.setDate(dateTime);
        shopUnitRepository.save(item);
        if (item.getParentId() != null) {
            Optional<ShopUnit> parent = shopUnitRepository.findById(item.getParentId());
            parent.ifPresent(shopUnit -> updateDate(shopUnit, dateTime));
        }
    }

    private Pair<Long, Long> getSum(ShopUnit item) {
        if (item.getType() == ShopUnitType.OFFER) return Pair.of(item.getPrice(), 1L);

        List<ShopUnit> children = shopUnitRepository.findAllChildrenById(item.getId());
        if (children == null || children.isEmpty()) return Pair.of(0L, 0L);

        Pair<Long, Long> sum = Pair.of(0L, 0L);
        for (ShopUnit unit : children){
            Pair<Long, Long> child = getSum(unit);
            sum = Pair.of(sum.getFirst() + child.getFirst(), sum.getSecond() + child.getSecond());
        }
        return sum;
    }

    private long getPrice(ShopUnit item) {
        Pair<Long, Long> sum = getSum(item);
        return sum.getFirst() / sum.getSecond();
    }

    public ShopUnit findById(UUID id) {
        Optional<ShopUnit> unit = shopUnitRepository.findById(id);
        if (unit.isEmpty()) return null;
        unit.get().setPrice(getPrice(unit.get()));

        List<ShopUnit> children = shopUnitRepository.findAllChildrenById(id);
        if (children.isEmpty()) unit.get().setChildren(null);
        else unit.get().setChildren(
                children.stream()
                        .map((c) -> findById(c.getId()))
                        .collect(Collectors.toList()));
        return unit.get();
    }

    public ShopUnit deleteById(UUID id) {
        ShopUnit unit = findById(id);
        if (unit == null) return null;
        shopUnitRepository.deleteAllChildrenById(id);
        shopUnitRepository.deleteById(id);
        return unit;
    }
}
