package dokerplp.yandexbackendschool.dto;

import dokerplp.yandexbackendschool.model.entity.ShopUnitType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ShopUnitStatisticUnit {
    private UUID id;
    private String name;
    private UUID parentId;
    private ShopUnitType type;
    private Long price;
    private LocalDateTime date;
}
