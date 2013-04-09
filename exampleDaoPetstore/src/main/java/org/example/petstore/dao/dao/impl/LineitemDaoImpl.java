package org.example.petstore.dao.dao.impl;
import it.mengoni.persistence.dao.AbstractRelationDao;
import java.util.ArrayList;
import it.mengoni.persistence.dao.JdbcHelper;
import org.example.petstore.dao.dto.impl.LineitemImpl;
import org.example.petstore.dao.dto.Lineitem;
import java.util.List;
import it.mengoni.persistence.dao.CharsetConverter;
import it.mengoni.persistence.dao.PkIntegerField;
import java.sql.Types;
import it.mengoni.persistence.dao.Field;
import org.javatuples.Tuple;
import org.javatuples.Pair;
import it.mengoni.persistence.dao.FieldImpl;
import it.mengoni.persistence.dao.IntegerField;
import org.example.petstore.dao.dao.LineitemDao;
import it.mengoni.persistence.dao.StringField;
public class LineitemDaoImpl extends AbstractRelationDao<Lineitem> implements LineitemDao {
private static final List<Field<Lineitem, ?>> fields = new ArrayList<Field<Lineitem, ?>>();
static {fields.add(new PkIntegerField<Lineitem>("orderid", "orderid", 10, Types.INTEGER) {
@Override
public void setValue(Integer value, Lineitem bean)  { bean.setOrderid(value); }
@Override
public Integer getValue(Lineitem bean) { return bean.getOrderid(); } 
});
fields.add(new PkIntegerField<Lineitem>("linenum", "linenum", 10, Types.INTEGER) {
@Override
public void setValue(Integer value, Lineitem bean)  { bean.setLinenum(value); }
@Override
public Integer getValue(Lineitem bean) { return bean.getLinenum(); } 
});
fields.add(new StringField<Lineitem>("itemid", "itemid", false, 10, Types.VARCHAR) {
@Override
public void setValue(String value, Lineitem bean)  { bean.setItemid(value); }
@Override
public String getValue(Lineitem bean) { return bean.getItemid(); } 
});
fields.add(new IntegerField<Lineitem>("quantity", "quantity", false, 10, Types.INTEGER) {
@Override
public void setValue(Integer value, Lineitem bean)  { bean.setQuantity(value); }
@Override
public Integer getValue(Lineitem bean) { return bean.getQuantity(); } 
});
fields.add(new FieldImpl<Lineitem, java.math.BigDecimal>("unitprice", "unitprice", false, 10, Types.NUMERIC) {
@Override
public void setValue(java.math.BigDecimal value, Lineitem bean)  { bean.setUnitprice(value); }
@Override
public java.math.BigDecimal getValue(Lineitem bean) { return bean.getUnitprice(); } 
});
}
public LineitemDaoImpl(JdbcHelper jdbcHelper, CharsetConverter charsetConverter) {super(jdbcHelper, charsetConverter, "lineitem", fields);}
    @Override
    public Lineitem newIstance() { return new LineitemImpl();	}
protected Tuple newKey(Integer orderid, Integer linenum) {
    return new Pair<Integer, Integer>(orderid, linenum);
}
public Lineitem getByPrimaryKey(Integer orderid, Integer linenum) {
    return get(new Pair<Integer, Integer>(orderid, linenum));
}
}
