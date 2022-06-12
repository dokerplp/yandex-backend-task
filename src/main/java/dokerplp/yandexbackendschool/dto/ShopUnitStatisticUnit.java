package dokerplp.yandexbackendschool.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import dokerplp.yandexbackendschool.model.entity.ShopUnit;
import dokerplp.yandexbackendschool.model.entity.ShopUnitType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShopUnitStatisticUnit {
    private UUID id;
    private String name;
    private UUID parentId;
    private ShopUnitType type;
    private Long price;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime date;

    public static ShopUnitStatisticUnit fromShopUnit(ShopUnit unit) {
        ShopUnitStatisticUnit susu = new ShopUnitStatisticUnit();
        susu.id = unit.getId();
        susu.name = unit.getName();
        susu.parentId = unit.getParentId();
        susu.type = unit.getType();
        susu.price = unit.getPrice();
        susu.date = unit.getDate();
        return susu;
    }
}
