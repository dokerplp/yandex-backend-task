package dokerplp.yandexbackendschool.util;

import dokerplp.yandexbackendschool.model.entity.ShopUnitType;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public class ShopUnitTypeConverter extends EnumType<ShopUnitType> {

    @Override
    public void nullSafeSet(
            PreparedStatement st,
            Object value,
            int index,
            SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        st.setObject(
                index,
                value != null ? ((Enum<?>) value).name() : null,
                Types.OTHER
        );
    }
}
