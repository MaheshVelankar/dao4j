package org.example.petstore.dao.dao.impl;
import it.mengoni.persistence.dao.AbstractRelationDao;
import java.util.ArrayList;
import it.mengoni.persistence.dao.JdbcHelper;
import org.example.petstore.dao.dto.Inventory;
import java.util.List;
import it.mengoni.persistence.dao.CharsetConverter;
import java.sql.Types;
import org.javatuples.Unit;
import it.mengoni.persistence.dao.Field;
import org.javatuples.Tuple;
import it.mengoni.persistence.dao.IntegerField;
import org.example.petstore.dao.dto.impl.InventoryImpl;
import org.example.petstore.dao.dao.InventoryDao;
import it.mengoni.persistence.dao.PkStringField;
public class InventoryDaoImpl extends AbstractRelationDao<Inventory> implements InventoryDao {
private static final List<Field<Inventory, ?>> fields = new ArrayList<Field<Inventory, ?>>();
static {fields.add(new PkStringField<Inventory>("itemid", "itemid", 10, Types.VARCHAR) {
@Override
public void setValue(String value, Inventory bean)  { bean.setItemid(value); }
@Override
public String getValue(Inventory bean) { return bean.getItemid(); } 
});
fields.add(new IntegerField<Inventory>("qty", "qty", false, 10, Types.INTEGER) {
@Override
public void setValue(Integer value, Inventory bean)  { bean.setQty(value); }
@Override
public Integer getValue(Inventory bean) { return bean.getQty(); } 
});
}
public InventoryDaoImpl(JdbcHelper jdbcHelper, CharsetConverter charsetConverter) {super(jdbcHelper, charsetConverter, "inventory", fields);}
    @Override
    public Inventory newIstance() { return new InventoryImpl();	}
protected Tuple newKey(String itemid) {
    return new Unit<String>(itemid);
}
public Inventory getByPrimaryKey(String itemid) {
    return get(new Unit<String>(itemid));
}
}
