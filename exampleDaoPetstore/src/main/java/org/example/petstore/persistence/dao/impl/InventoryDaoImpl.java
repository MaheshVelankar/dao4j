package org.example.petstore.persistence.dao.impl;

import it.mengoni.persistence.dao.AbstractRelationDao;
import it.mengoni.persistence.dao.CharsetConverter;
import it.mengoni.persistence.dao.Field;
import it.mengoni.persistence.dao.IntegerField;
import it.mengoni.persistence.dao.JdbcHelper;
import it.mengoni.persistence.dao.PkStringField;

import org.example.petstore.persistence.dao.InventoryDao;
import org.example.petstore.persistence.dto.Inventory;
import org.example.petstore.persistence.dto.impl.InventoryImpl;

import org.javatuples.Tuple;
import org.javatuples.Unit;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;


public class InventoryDaoImpl extends AbstractRelationDao<Inventory>
    implements InventoryDao {
    private static final List<Field<Inventory, ?>> fields = new ArrayList<Field<Inventory, ?>>();

    static {
        fields.add(new PkStringField<Inventory>("itemid", "itemid", 10,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Inventory bean) {
                    bean.setItemid(value);
                }

                @Override
                public String getValue(Inventory bean) {
                    return bean.getItemid();
                }
            });
        fields.add(new IntegerField<Inventory>("qty", "qty", false, 10,
                Types.INTEGER) {
                @Override
                public void setValue(Integer value, Inventory bean) {
                    bean.setQty(value);
                }

                @Override
                public Integer getValue(Inventory bean) {
                    return bean.getQty();
                }
            });
    }

    public InventoryDaoImpl(JdbcHelper jdbcHelper,
        CharsetConverter charsetConverter) {
        super(jdbcHelper, charsetConverter, "inventory", fields);
    }

    @Override
    public Inventory newIstance() {
        return new InventoryImpl();
    }

    protected Tuple newKey(String itemid) {
        return new Unit<String>(itemid);
    }

    public Inventory getByPrimaryKey(String itemid) {
        return get(new Unit<String>(itemid));
    }
}
