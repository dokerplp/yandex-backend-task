package dokerplp.yandexbackendschool.model.repository;

import dokerplp.yandexbackendschool.model.entity.ShopUnit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShopUnitRepository extends CrudRepository<ShopUnit, UUID> {
}
