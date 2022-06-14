package dokerplp.yandexbackendschool.model.repository;

import dokerplp.yandexbackendschool.model.entity.ShopUnit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ShopUnitRepository extends CrudRepository<ShopUnit, UUID> {
    @Query("select u from ShopUnit u where u.parentId = ?1")
    List<ShopUnit> findAllChildrenById(UUID id);

    @Query("select u from ShopUnit u where u.date >= ?1 and u.date <= ?2 and u.type <> 'CATEGORY'")
    List<ShopUnit> findAllSalesByDate(LocalDateTime start, LocalDateTime end);
}
