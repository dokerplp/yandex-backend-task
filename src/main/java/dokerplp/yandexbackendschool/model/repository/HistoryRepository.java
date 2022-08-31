package dokerplp.yandexbackendschool.model.repository;

import dokerplp.yandexbackendschool.model.entity.History;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface HistoryRepository extends CrudRepository<History, Long> {

    @Query("select h from History h where h.shopUnitId = ?1 and h.date >= ?2 and h.date <= ?3")
    List<History> getStatistic(UUID shopUnitId, LocalDateTime dateStart, LocalDateTime dateEnd);

    @Query("select h from History h where h.id = (select max (hh.id) from History hh where hh.shopUnitId = ?1)")
    History getLatest(UUID shopUnitId);

    @Modifying
    @Transactional
    @Query("delete from History h where h.shopUnitId = ?1")
    void deleteAllByShopUnitId(UUID shopUnitId);
}
