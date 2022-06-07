package dokerplp.yandexbackendschool.dto;

import dokerplp.yandexbackendschool.model.entity.ShopUnit;
import dokerplp.yandexbackendschool.model.entity.ShopUnitType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ShopUnitImport {
    private UUID id;
    private String name;
    private UUID parentId;
    private ShopUnitType type;
    private Long price;

    public ShopUnit toShopUnit(LocalDateTime date) {
        return new ShopUnit.ShopUnitBuilder()
                .setId(id)
                .setName(name)
                .setParentId(parentId)
                .setType(type)
                .setPrice(price)
                .setDate(date)
                .build();
    }
}
