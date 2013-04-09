package org.example.petstore.dao.dao.impl;
import it.mengoni.persistence.dao.AbstractRelationDao;
import java.util.ArrayList;
import it.mengoni.persistence.dao.JdbcHelper;
import org.example.petstore.dao.dao.OrdersDao;
import org.example.petstore.dao.dto.impl.OrdersImpl;
import java.util.List;
import it.mengoni.persistence.dao.CharsetConverter;
import it.mengoni.persistence.dao.PkIntegerField;
import java.sql.Types;
import org.javatuples.Unit;
import it.mengoni.persistence.dao.Field;
import org.javatuples.Tuple;
import org.example.petstore.dao.dto.Orders;
import it.mengoni.persistence.dao.FieldImpl;
import it.mengoni.persistence.dao.StringField;
public class OrdersDaoImpl extends AbstractRelationDao<Orders> implements OrdersDao {
private static final List<Field<Orders, ?>> fields = new ArrayList<Field<Orders, ?>>();
static {fields.add(new PkIntegerField<Orders>("orderid", "orderid", 10, Types.INTEGER) {
@Override
public void setValue(Integer value, Orders bean)  { bean.setOrderid(value); }
@Override
public Integer getValue(Orders bean) { return bean.getOrderid(); } 
});
fields.add(new StringField<Orders>("userid", "userid", false, 80, Types.VARCHAR) {
@Override
public void setValue(String value, Orders bean)  { bean.setUserid(value); }
@Override
public String getValue(Orders bean) { return bean.getUserid(); } 
});
fields.add(new FieldImpl<Orders, java.sql.Date>("orderdate", "orderdate", false, 13, Types.DATE) {
@Override
public void setValue(java.sql.Date value, Orders bean)  { bean.setOrderdate(value); }
@Override
public java.sql.Date getValue(Orders bean) { return bean.getOrderdate(); } 
});
fields.add(new StringField<Orders>("shipaddr1", "shipaddr1", false, 80, Types.VARCHAR) {
@Override
public void setValue(String value, Orders bean)  { bean.setShipaddr1(value); }
@Override
public String getValue(Orders bean) { return bean.getShipaddr1(); } 
});
fields.add(new StringField<Orders>("shipaddr2", "shipaddr2", true, 80, Types.VARCHAR) {
@Override
public void setValue(String value, Orders bean)  { bean.setShipaddr2(value); }
@Override
public String getValue(Orders bean) { return bean.getShipaddr2(); } 
});
fields.add(new StringField<Orders>("shipcity", "shipcity", false, 80, Types.VARCHAR) {
@Override
public void setValue(String value, Orders bean)  { bean.setShipcity(value); }
@Override
public String getValue(Orders bean) { return bean.getShipcity(); } 
});
fields.add(new StringField<Orders>("shipstate", "shipstate", false, 80, Types.VARCHAR) {
@Override
public void setValue(String value, Orders bean)  { bean.setShipstate(value); }
@Override
public String getValue(Orders bean) { return bean.getShipstate(); } 
});
fields.add(new StringField<Orders>("shipzip", "shipzip", false, 20, Types.VARCHAR) {
@Override
public void setValue(String value, Orders bean)  { bean.setShipzip(value); }
@Override
public String getValue(Orders bean) { return bean.getShipzip(); } 
});
fields.add(new StringField<Orders>("shipcountry", "shipcountry", false, 20, Types.VARCHAR) {
@Override
public void setValue(String value, Orders bean)  { bean.setShipcountry(value); }
@Override
public String getValue(Orders bean) { return bean.getShipcountry(); } 
});
fields.add(new StringField<Orders>("billaddr1", "billaddr1", false, 80, Types.VARCHAR) {
@Override
public void setValue(String value, Orders bean)  { bean.setBilladdr1(value); }
@Override
public String getValue(Orders bean) { return bean.getBilladdr1(); } 
});
fields.add(new StringField<Orders>("billaddr2", "billaddr2", true, 80, Types.VARCHAR) {
@Override
public void setValue(String value, Orders bean)  { bean.setBilladdr2(value); }
@Override
public String getValue(Orders bean) { return bean.getBilladdr2(); } 
});
fields.add(new StringField<Orders>("billcity", "billcity", false, 80, Types.VARCHAR) {
@Override
public void setValue(String value, Orders bean)  { bean.setBillcity(value); }
@Override
public String getValue(Orders bean) { return bean.getBillcity(); } 
});
fields.add(new StringField<Orders>("billstate", "billstate", false, 80, Types.VARCHAR) {
@Override
public void setValue(String value, Orders bean)  { bean.setBillstate(value); }
@Override
public String getValue(Orders bean) { return bean.getBillstate(); } 
});
fields.add(new StringField<Orders>("billzip", "billzip", false, 20, Types.VARCHAR) {
@Override
public void setValue(String value, Orders bean)  { bean.setBillzip(value); }
@Override
public String getValue(Orders bean) { return bean.getBillzip(); } 
});
fields.add(new StringField<Orders>("billcountry", "billcountry", false, 20, Types.VARCHAR) {
@Override
public void setValue(String value, Orders bean)  { bean.setBillcountry(value); }
@Override
public String getValue(Orders bean) { return bean.getBillcountry(); } 
});
fields.add(new StringField<Orders>("courier", "courier", false, 80, Types.VARCHAR) {
@Override
public void setValue(String value, Orders bean)  { bean.setCourier(value); }
@Override
public String getValue(Orders bean) { return bean.getCourier(); } 
});
fields.add(new FieldImpl<Orders, java.math.BigDecimal>("totalprice", "totalprice", false, 10, Types.NUMERIC) {
@Override
public void setValue(java.math.BigDecimal value, Orders bean)  { bean.setTotalprice(value); }
@Override
public java.math.BigDecimal getValue(Orders bean) { return bean.getTotalprice(); } 
});
fields.add(new StringField<Orders>("billtofirstname", "billtofirstname", false, 80, Types.VARCHAR) {
@Override
public void setValue(String value, Orders bean)  { bean.setBilltofirstname(value); }
@Override
public String getValue(Orders bean) { return bean.getBilltofirstname(); } 
});
fields.add(new StringField<Orders>("billtolastname", "billtolastname", false, 80, Types.VARCHAR) {
@Override
public void setValue(String value, Orders bean)  { bean.setBilltolastname(value); }
@Override
public String getValue(Orders bean) { return bean.getBilltolastname(); } 
});
fields.add(new StringField<Orders>("shiptofirstname", "shiptofirstname", false, 80, Types.VARCHAR) {
@Override
public void setValue(String value, Orders bean)  { bean.setShiptofirstname(value); }
@Override
public String getValue(Orders bean) { return bean.getShiptofirstname(); } 
});
fields.add(new StringField<Orders>("shiptolastname", "shiptolastname", false, 80, Types.VARCHAR) {
@Override
public void setValue(String value, Orders bean)  { bean.setShiptolastname(value); }
@Override
public String getValue(Orders bean) { return bean.getShiptolastname(); } 
});
fields.add(new StringField<Orders>("creditcard", "creditcard", false, 80, Types.VARCHAR) {
@Override
public void setValue(String value, Orders bean)  { bean.setCreditcard(value); }
@Override
public String getValue(Orders bean) { return bean.getCreditcard(); } 
});
fields.add(new StringField<Orders>("exprdate", "exprdate", false, 7, Types.VARCHAR) {
@Override
public void setValue(String value, Orders bean)  { bean.setExprdate(value); }
@Override
public String getValue(Orders bean) { return bean.getExprdate(); } 
});
fields.add(new StringField<Orders>("cardtype", "cardtype", false, 80, Types.VARCHAR) {
@Override
public void setValue(String value, Orders bean)  { bean.setCardtype(value); }
@Override
public String getValue(Orders bean) { return bean.getCardtype(); } 
});
fields.add(new StringField<Orders>("locale", "locale", false, 80, Types.VARCHAR) {
@Override
public void setValue(String value, Orders bean)  { bean.setLocale(value); }
@Override
public String getValue(Orders bean) { return bean.getLocale(); } 
});
}
public OrdersDaoImpl(JdbcHelper jdbcHelper, CharsetConverter charsetConverter) {super(jdbcHelper, charsetConverter, "orders", fields);}
    @Override
    public Orders newIstance() { return new OrdersImpl();	}
protected Tuple newKey(Integer orderid) {
    return new Unit<Integer>(orderid);
}
public Orders getByPrimaryKey(Integer orderid) {
    return get(new Unit<Integer>(orderid));
}
}
