package dokerplp.yandexbackendschool.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopUnitImportRequest {
    private List<ShopUnitImport> items;
    private LocalDateTime updateDate;

}
