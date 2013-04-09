package org.example.petstore.dao.dao.impl;
import it.mengoni.persistence.dao.AbstractRelationDao;
import java.util.ArrayList;
import it.mengoni.persistence.dao.JdbcHelper;
import java.util.List;
import it.mengoni.persistence.dao.CharsetConverter;
import java.sql.Types;
import org.javatuples.Unit;
import it.mengoni.persistence.dao.Field;
import org.example.petstore.dao.dto.Product;
import org.javatuples.Tuple;
import org.example.petstore.dao.dto.impl.ProductImpl;
import it.mengoni.persistence.dao.StringField;
import org.example.petstore.dao.dao.ProductDao;
import it.mengoni.persistence.dao.PkStringField;
public class ProductDaoImpl extends AbstractRelationDao<Product> implements ProductDao {
private static final List<Field<Product, ?>> fields = new ArrayList<Field<Product, ?>>();
static {fields.add(new PkStringField<Product>("productid", "productid", 10, Types.VARCHAR) {
@Override
public void setValue(String value, Product bean)  { bean.setProductid(value); }
@Override
public String getValue(Product bean) { return bean.getProductid(); } 
});
fields.add(new StringField<Product>("category", "category", false, 10, Types.VARCHAR) {
@Override
public void setValue(String value, Product bean)  { bean.setCategory(value); }
@Override
public String getValue(Product bean) { return bean.getCategory(); } 
});
fields.add(new StringField<Product>("name", "name", true, 80, Types.VARCHAR) {
@Override
public void setValue(String value, Product bean)  { bean.setName(value); }
@Override
public String getValue(Product bean) { return bean.getName(); } 
});
fields.add(new StringField<Product>("descn", "descn", true, 255, Types.VARCHAR) {
@Override
public void setValue(String value, Product bean)  { bean.setDescn(value); }
@Override
public String getValue(Product bean) { return bean.getDescn(); } 
});
}
public ProductDaoImpl(JdbcHelper jdbcHelper, CharsetConverter charsetConverter) {super(jdbcHelper, charsetConverter, "product", fields);}
    @Override
    public Product newIstance() { return new ProductImpl();	}
protected Tuple newKey(String productid) {
    return new Unit<String>(productid);
}
public Product getByPrimaryKey(String productid) {
    return get(new Unit<String>(productid));
}
}
