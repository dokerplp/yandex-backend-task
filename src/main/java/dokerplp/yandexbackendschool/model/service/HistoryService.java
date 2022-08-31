package dokerplp.yandexbackendschool.model.service;

import dokerplp.yandexbackendschool.model.entity.History;
import dokerplp.yandexbackendschool.model.entity.ShopUnit;
import dokerplp.yandexbackendschool.model.entity.ShopUnitType;
import dokerplp.yandexbackendschool.model.repository.HistoryRepository;
import dokerplp.yandexbackendschool.model.repository.ShopUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private ShopUnitRepository shopUnitRepository;

    public void updateHistory(Set<History> histories) {
        Map<UUID, History> items = new HashMap<>();
        for (History history : histories)
            items = getPrice(history.getShopUnitId(), items);

        Set<History> units = new HashSet<>();
        for (History h : histories) {
            h.setPrice(items.get(h.getShopUnitId()).getPrice());
            units.add(h);
        }

        historyRepository.saveAll(units);
    }

    private Map<UUID, History> getPrice(UUID id, Map<UUID, History> used) {
        Optional<ShopUnit> optional = shopUnitRepository.findById(id);
        if (optional.isEmpty()) return used;
        History unit = new History(optional.get());

        if (used.containsKey(id)) return used;
        if (unit.getType() == ShopUnitType.OFFER) {
            unit.setAmount(1);
            unit.setTotal(unit.getPrice());
            used.put(id, unit);
            return used;
        }

        long total = 0;
        long amount = 0;
        for (ShopUnit child : shopUnitRepository.findAllChildrenById(id)) {
            used = getPrice(child.getId(), used);
            total += used.get(child.getId()).getTotal();
            amount += used.get(child.getId()).getAmount();
        }

        unit.setTotal(total);
        unit.setAmount(amount);
        unit.setPrice(amount == 0 ? null : total / amount);
        used.put(id, unit);
        return used;
    }


    public Long getLatestPrice(UUID id) {
        History h = historyRepository.getLatest(id);
        return h == null ? null : h.getPrice();
    }

    public void deleteAllByShopUnitId(UUID id) {
        historyRepository.deleteAllByShopUnitId(id);
    }

    public List<History> getStatistic(UUID shopUnitId, LocalDateTime dateStart, LocalDateTime dateEnd) {
        return historyRepository.getStatistic(shopUnitId, dateStart, dateEnd);
    }
}