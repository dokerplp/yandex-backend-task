package dokerplp.yandexbackendschool.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import dokerplp.yandexbackendschool.util.ShopUnitTypeConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "SHOPUNIT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@TypeDef(
        name = "SHOPUNITTYPE",
        typeClass = ShopUnitTypeConverter.class
)
public class ShopUnit {
    @Id
    @Column(name = "ID", nullable = false)
    protected UUID id;

    @Column(name = "NAME", nullable = false)
    protected String name;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Column(name = "DATE", nullable = false)
    protected LocalDateTime date;

    @Column(name = "PARENTID")
    protected UUID parentId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "TYPE", nullable = false)
    @Type(type = "SHOPUNITTYPE")
    protected ShopUnitType type;

    @Column(name = "PRICE")
    protected Long price;

    @Transient
    protected List<ShopUnit> children;

    public static class ShopUnitBuilder {
        private final ShopUnit shopUnit = new ShopUnit();

        public ShopUnitBuilder setId(UUID id) {
            shopUnit.setId(id);
            return this;
        }

        public ShopUnitBuilder setName(String name) {
            shopUnit.setName(name);
            return this;
        }

        public ShopUnitBuilder setDate(LocalDateTime date) {
            shopUnit.setDate(date);
            return this;
        }

        public ShopUnitBuilder setParentId(UUID parentId) {
            shopUnit.setParentId(parentId);
            return this;
        }

        public ShopUnitBuilder setType(ShopUnitType type) {
            shopUnit.setType(type);
            return this;
        }

        public ShopUnitBuilder setPrice(Long price) {
            shopUnit.setPrice(price);
            return this;
        }

        public ShopUnit build() {
            return shopUnit;
        }
    }
}
