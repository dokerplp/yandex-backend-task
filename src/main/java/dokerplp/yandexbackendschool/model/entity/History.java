package dokerplp.yandexbackendschool.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "HISTORY")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "SHOPUNITID", nullable = false)
    private UUID shopUnitId;

    @Column(name = "NAME", nullable = false)
    private String name;

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
    private long total;

    @Transient
    private long amount;

    public History(ShopUnit unit) {
        this.shopUnitId = unit.getId();
        this.name = unit.getName();
        this.date = unit.getDate();
        this.parentId = unit.getParentId();
        this.type = unit.getType();
        this.price = unit.getPrice();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        History history = (History) o;
        return shopUnitId.equals(history.shopUnitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shopUnitId);
    }
}
