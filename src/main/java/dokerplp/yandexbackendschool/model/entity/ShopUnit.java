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
import java.util.Objects;
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
    private UUID id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    @Column(name = "DATE", nullable = false)
    private LocalDateTime date;

    @Column(name = "PARENTID")
    private UUID parentId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "TYPE", nullable = false)
    @Type(type = "SHOPUNITTYPE")
    private ShopUnitType type;

    @Column(name = "PRICE")
    private Long price;

    @Transient
    private List<ShopUnit> children;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShopUnit unit = (ShopUnit) o;
        return id.equals(unit.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "ShopUnit{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", parentId=" + parentId +
                ", type=" + type +
                ", price=" + price +
                ", children=" + children +
                '}';
    }

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
