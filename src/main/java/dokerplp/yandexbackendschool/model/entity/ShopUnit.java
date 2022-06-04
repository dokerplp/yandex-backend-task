package dokerplp.yandexbackendschool.model.entity;

import dokerplp.yandexbackendschool.util.ShopUnitTypeConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(
            name = "SHOPUNITCHILDREN",
            joinColumns = {
                    @JoinColumn(name = "CHILDID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "PARENTID")
            }
    )
    protected List<ShopUnit> children;

    public static class ShopUnitFactory {
        private final ShopUnit shopUnit = new ShopUnit();

        public ShopUnitFactory setId(UUID id) {
            shopUnit.setId(id);
            return this;
        }

        public ShopUnitFactory setName(String name) {
            shopUnit.setName(name);
            return this;
        }

        public ShopUnitFactory setDate(LocalDateTime date) {
            shopUnit.setDate(date);
            return this;
        }

        public ShopUnitFactory setParentId(UUID parentId) {
            shopUnit.setId(parentId);
            return this;
        }

        public ShopUnitFactory setType(ShopUnitType type) {
            shopUnit.setType(type);
            return this;
        }

        public ShopUnitFactory setPrice(Long price) {
            shopUnit.setPrice(price);
            return this;
        }

        public ShopUnit build() {
            return shopUnit;
        }
    }
}
