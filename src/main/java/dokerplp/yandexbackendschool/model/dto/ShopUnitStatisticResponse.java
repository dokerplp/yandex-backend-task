package dokerplp.yandexbackendschool.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class ShopUnitStatisticResponse {
    private ArrayList<ShopUnitStatisticUnit> items;
}
