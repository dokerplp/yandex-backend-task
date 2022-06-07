package dokerplp.yandexbackendschool.model.service;

import dokerplp.yandexbackendschool.exception.BadRequestException;
import dokerplp.yandexbackendschool.model.entity.ShopUnit;
import dokerplp.yandexbackendschool.model.entity.ShopUnitType;
import dokerplp.yandexbackendschool.model.repository.ShopUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShopUnitService {
    @Autowired
    private ShopUnitRepository shopUnitRepository;

    public void saveAll(List<ShopUnit> items) {
        for (ShopUnit unit : items) {
            Optional<ShopUnit> parent = shopUnitRepository.findById(unit.getId());
            if (parent.isPresent() && parent.get().getType() == ShopUnitType.OFFER)
                throw new BadRequestException();
            shopUnitRepository.save(unit);
        }
    }



    public ShopUnit findById(UUID id) {
        Optional<ShopUnit> unit = shopUnitRepository.findById(id);
        if (unit.isEmpty()) return null;

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
