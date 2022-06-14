package dokerplp.yandexbackendschool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopUnitStatisticResponse {
    private List<ShopUnitStatisticUnit> items;
}
