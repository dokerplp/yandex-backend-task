package dokerplp.yandexbackendschool.model.service;

import dokerplp.yandexbackendschool.model.entity.ShopUnit;
import dokerplp.yandexbackendschool.model.repository.ShopUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShopUnitService {

    @Autowired
    private ShopUnitRepository shopUnitRepository;

    public void saveAll(List<ShopUnit> items) {
        shopUnitRepository.saveAll(items);
    }

    public ShopUnit findById(UUID id) {
        List<ShopUnit> children = shopUnitRepository.findAllChildrenById(id);
        if (children.isEmpty()) children = null;
        Optional<ShopUnit> unit = shopUnitRepository.findById(id);
        if (unit.isEmpty()) return null;
        else {
            ShopUnit shopUnit = unit.get();
            shopUnit.setChildren(children);
            return shopUnit;
        }
    }

    public ShopUnit deleteById(UUID id) {
        ShopUnit unit = findById(id);
        if (unit == null) return null;
        shopUnitRepository.deleteById(id);
        shopUnitRepository.deleteAllChildrenById(id);
        return unit;
    }
}
