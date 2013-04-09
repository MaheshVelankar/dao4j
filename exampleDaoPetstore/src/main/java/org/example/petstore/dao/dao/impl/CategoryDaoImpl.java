package org.example.petstore.dao.dao.impl;
import it.mengoni.persistence.dao.AbstractRelationDao;
import java.util.ArrayList;
import it.mengoni.persistence.dao.JdbcHelper;
import org.example.petstore.dao.dto.Category;
import java.util.List;
import it.mengoni.persistence.dao.CharsetConverter;
import org.example.petstore.dao.dto.impl.CategoryImpl;
import java.sql.Types;
import org.javatuples.Unit;
import org.example.petstore.dao.dao.CategoryDao;
import it.mengoni.persistence.dao.Field;
import org.javatuples.Tuple;
import it.mengoni.persistence.dao.StringField;
import it.mengoni.persistence.dao.PkStringField;
public class CategoryDaoImpl extends AbstractRelationDao<Category> implements CategoryDao {
private static final List<Field<Category, ?>> fields = new ArrayList<Field<Category, ?>>();
static {fields.add(new PkStringField<Category>("catid", "catid", 10, Types.VARCHAR) {
@Override
public void setValue(String value, Category bean)  { bean.setCatid(value); }
@Override
public String getValue(Category bean) { return bean.getCatid(); } 
});
fields.add(new StringField<Category>("name", "name", true, 80, Types.VARCHAR) {
@Override
public void setValue(String value, Category bean)  { bean.setName(value); }
@Override
public String getValue(Category bean) { return bean.getName(); } 
});
fields.add(new StringField<Category>("descn", "descn", true, 255, Types.VARCHAR) {
@Override
public void setValue(String value, Category bean)  { bean.setDescn(value); }
@Override
public String getValue(Category bean) { return bean.getDescn(); } 
});
}
public CategoryDaoImpl(JdbcHelper jdbcHelper, CharsetConverter charsetConverter) {super(jdbcHelper, charsetConverter, "category", fields);}
    @Override
    public Category newIstance() { return new CategoryImpl();	}
protected Tuple newKey(String catid) {
    return new Unit<String>(catid);
}
public Category getByPrimaryKey(String catid) {
    return get(new Unit<String>(catid));
}
}
