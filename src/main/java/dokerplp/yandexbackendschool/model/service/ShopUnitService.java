package dokerplp.yandexbackendschool.model.service;

import dokerplp.yandexbackendschool.model.entity.ShopUnit;
import dokerplp.yandexbackendschool.model.entity.ShopUnitType;
import dokerplp.yandexbackendschool.model.repository.ShopUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ShopUnitService {
    @Autowired
    private ShopUnitRepository shopUnitRepository;

    public void saveAll(List<ShopUnit> items, LocalDateTime dateTime) {
        shopUnitRepository.saveAll(items);

        Set<ShopUnit> units = new HashSet<>();
        for (ShopUnit unit : items) {
            if (unit.getParentId() == null) continue;
            Optional<ShopUnit> parent = shopUnitRepository.findById(unit.getParentId());
            while (parent.isPresent()) {
                units.add(parent.get());
                if (parent.get().getParentId() == null) break;
                parent = shopUnitRepository.findById(parent.get().getParentId());
            }
        }

        units.forEach((e) -> e.setDate(dateTime));
        shopUnitRepository.saveAll(units);
    }

    public ShopUnit findById(UUID id) {
        Optional<ShopUnit> optional = shopUnitRepository.findById(id);
        if (optional.isEmpty()) return null;
        ShopUnit unit = optional.get();

        if (unit.getType() != ShopUnitType.CATEGORY) {
            unit.setTotal(unit.getPrice());
            unit.setAmount(1);
            return unit;
        }

        AtomicLong total = new AtomicLong();
        AtomicLong amount = new AtomicLong();
        List<ShopUnit> children = shopUnitRepository.findAllChildrenById(id).stream()
                .map((e) -> {
                    ShopUnit child = findById(e.getId());
                    total.addAndGet(child.getTotal());
                    amount.addAndGet(child.getAmount());
                    return child;
                })
                .collect(Collectors.toList());

        unit.setTotal(total.get());
        unit.setAmount(amount.get());
        unit.setPrice(amount.get() == 0 ? null : total.get() / amount.get());
        unit.setChildren(children);

        if (children.isEmpty()) unit.setChildren(null);
        return unit;
    }

    public ShopUnit deleteById(UUID id) {
        Optional<ShopUnit> optional = shopUnitRepository.findById(id);
        if (optional.isEmpty()) return null;
        ShopUnit unit = optional.get();

        List<ShopUnit> children = shopUnitRepository.findAllChildrenById(id);
        children.forEach((e) -> deleteById(e.getId()));

        shopUnitRepository.deleteById(unit.getId());
        return unit;
    }

    public List<ShopUnit> findAllSalesByDate(LocalDateTime end) {
        return shopUnitRepository.findAllSalesByDate(end.minusDays(1), end);
    }
}
