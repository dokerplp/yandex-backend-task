package dokerplp.yandexbackendschool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
public class ShopUnitImportRequest {
    private ArrayList<ShopUnitImport> items;
    private LocalDateTime updateDate;

}
