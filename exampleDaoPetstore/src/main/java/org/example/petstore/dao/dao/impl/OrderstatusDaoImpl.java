package org.example.petstore.dao.dao.impl;
import it.mengoni.persistence.dao.AbstractRelationDao;
import java.util.ArrayList;
import it.mengoni.persistence.dao.JdbcHelper;
import org.example.petstore.dao.dto.impl.OrderstatusImpl;
import org.example.petstore.dao.dto.Orderstatus;
import java.util.List;
import it.mengoni.persistence.dao.CharsetConverter;
import it.mengoni.persistence.dao.PkIntegerField;
import java.sql.Types;
import org.example.petstore.dao.dao.OrderstatusDao;
import it.mengoni.persistence.dao.Field;
import org.javatuples.Tuple;
import org.javatuples.Pair;
import it.mengoni.persistence.dao.FieldImpl;
import it.mengoni.persistence.dao.StringField;
public class OrderstatusDaoImpl extends AbstractRelationDao<Orderstatus> implements OrderstatusDao {
private static final List<Field<Orderstatus, ?>> fields = new ArrayList<Field<Orderstatus, ?>>();
static {fields.add(new PkIntegerField<Orderstatus>("orderid", "orderid", 10, Types.INTEGER) {
@Override
public void setValue(Integer value, Orderstatus bean)  { bean.setOrderid(value); }
@Override
public Integer getValue(Orderstatus bean) { return bean.getOrderid(); } 
});
fields.add(new PkIntegerField<Orderstatus>("linenum", "linenum", 10, Types.INTEGER) {
@Override
public void setValue(Integer value, Orderstatus bean)  { bean.setLinenum(value); }
@Override
public Integer getValue(Orderstatus bean) { return bean.getLinenum(); } 
});
fields.add(new FieldImpl<Orderstatus, java.sql.Date>("\"timestamp\"", "timestamp", false, 13, Types.DATE) {
@Override
public void setValue(java.sql.Date value, Orderstatus bean)  { bean.setTimestamp(value); }
@Override
public java.sql.Date getValue(Orderstatus bean) { return bean.getTimestamp(); } 
});
fields.add(new StringField<Orderstatus>("status", "status", false, 2, Types.VARCHAR) {
@Override
public void setValue(String value, Orderstatus bean)  { bean.setStatus(value); }
@Override
public String getValue(Orderstatus bean) { return bean.getStatus(); } 
});
}
public OrderstatusDaoImpl(JdbcHelper jdbcHelper, CharsetConverter charsetConverter) {super(jdbcHelper, charsetConverter, "orderstatus", fields);}
    @Override
    public Orderstatus newIstance() { return new OrderstatusImpl();	}
protected Tuple newKey(Integer orderid, Integer linenum) {
    return new Pair<Integer, Integer>(orderid, linenum);
}
public Orderstatus getByPrimaryKey(Integer orderid, Integer linenum) {
    return get(new Pair<Integer, Integer>(orderid, linenum));
}
}
