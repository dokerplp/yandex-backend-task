package dokerplp.yandexbackendschool.model.repository;

import dokerplp.yandexbackendschool.model.entity.ShopUnit;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface ShopUnitRepository extends CrudRepository<ShopUnit, UUID> {
    @Query("select u from ShopUnit u where u.parentId = ?1")
    List<ShopUnit> findAllChildrenById(UUID id);

    @Modifying
    @Transactional
    @Query("delete from ShopUnit u where u.parentId = ?1")
    void deleteAllChildrenById(UUID id);
}
