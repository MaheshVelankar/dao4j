package org.example.petstore.dao.dao.impl;
import it.mengoni.persistence.dao.AbstractRelationDao;
import java.util.ArrayList;
import it.mengoni.persistence.dao.JdbcHelper;
import org.example.petstore.dao.dto.impl.SignonImpl;
import java.util.List;
import it.mengoni.persistence.dao.CharsetConverter;
import java.sql.Types;
import org.javatuples.Unit;
import it.mengoni.persistence.dao.Field;
import org.javatuples.Tuple;
import org.example.petstore.dao.dao.SignonDao;
import it.mengoni.persistence.dao.StringField;
import it.mengoni.persistence.dao.PkStringField;
import org.example.petstore.dao.dto.Signon;
public class SignonDaoImpl extends AbstractRelationDao<Signon> implements SignonDao {
private static final List<Field<Signon, ?>> fields = new ArrayList<Field<Signon, ?>>();
static {fields.add(new PkStringField<Signon>("username", "username", 25, Types.VARCHAR) {
@Override
public void setValue(String value, Signon bean)  { bean.setUsername(value); }
@Override
public String getValue(Signon bean) { return bean.getUsername(); } 
});
fields.add(new StringField<Signon>("password", "password", false, 25, Types.VARCHAR) {
@Override
public void setValue(String value, Signon bean)  { bean.setPassword(value); }
@Override
public String getValue(Signon bean) { return bean.getPassword(); } 
});
}
public SignonDaoImpl(JdbcHelper jdbcHelper, CharsetConverter charsetConverter) {super(jdbcHelper, charsetConverter, "signon", fields);}
    @Override
    public Signon newIstance() { return new SignonImpl();	}
protected Tuple newKey(String username) {
    return new Unit<String>(username);
}
public Signon getByPrimaryKey(String username) {
    return get(new Unit<String>(username));
}
}
