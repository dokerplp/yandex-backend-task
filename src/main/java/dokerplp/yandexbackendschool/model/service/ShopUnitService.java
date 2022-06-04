package dokerplp.yandexbackendschool.model.service;

import dokerplp.yandexbackendschool.model.entity.ShopUnit;
import dokerplp.yandexbackendschool.model.repository.ShopUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopUnitService {

    @Autowired
    private ShopUnitRepository shopUnitRepository;

    public void saveAll(List<ShopUnit> items) {
        shopUnitRepository.saveAll(items);
    }
}
