package org.example.petstore.dao.dao.impl;
import it.mengoni.persistence.dao.AbstractRelationDao;
import java.util.ArrayList;
import it.mengoni.persistence.dao.JdbcHelper;
import org.example.petstore.dao.dto.impl.SequenceImpl;
import org.example.petstore.dao.dao.SequenceDao;
import java.util.List;
import it.mengoni.persistence.dao.CharsetConverter;
import java.sql.Types;
import org.javatuples.Unit;
import it.mengoni.persistence.dao.Field;
import org.javatuples.Tuple;
import org.example.petstore.dao.dto.Sequence;
import it.mengoni.persistence.dao.IntegerField;
import it.mengoni.persistence.dao.PkStringField;
public class SequenceDaoImpl extends AbstractRelationDao<Sequence> implements SequenceDao {
private static final List<Field<Sequence, ?>> fields = new ArrayList<Field<Sequence, ?>>();
static {fields.add(new PkStringField<Sequence>("name", "name", 30, Types.VARCHAR) {
@Override
public void setValue(String value, Sequence bean)  { bean.setName(value); }
@Override
public String getValue(Sequence bean) { return bean.getName(); } 
});
fields.add(new IntegerField<Sequence>("nextid", "nextid", false, 10, Types.INTEGER) {
@Override
public void setValue(Integer value, Sequence bean)  { bean.setNextid(value); }
@Override
public Integer getValue(Sequence bean) { return bean.getNextid(); } 
});
}
public SequenceDaoImpl(JdbcHelper jdbcHelper, CharsetConverter charsetConverter) {super(jdbcHelper, charsetConverter, "sequence", fields);}
    @Override
    public Sequence newIstance() { return new SequenceImpl();	}
protected Tuple newKey(String name) {
    return new Unit<String>(name);
}
public Sequence getByPrimaryKey(String name) {
    return get(new Unit<String>(name));
}
}
