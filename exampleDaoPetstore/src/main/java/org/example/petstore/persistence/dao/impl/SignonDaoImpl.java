package org.example.petstore.persistence.dao.impl;

import it.mengoni.persistence.dao.AbstractRelationDao;
import it.mengoni.persistence.dao.CharsetConverter;
import it.mengoni.persistence.dao.Field;
import it.mengoni.persistence.dao.JdbcHelper;
import it.mengoni.persistence.dao.PkStringField;
import it.mengoni.persistence.dao.StringField;

import org.example.petstore.persistence.dao.SignonDao;
import org.example.petstore.persistence.dto.Signon;
import org.example.petstore.persistence.dto.impl.SignonImpl;

import org.javatuples.Tuple;
import org.javatuples.Unit;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;


public class SignonDaoImpl extends AbstractRelationDao<Signon>
    implements SignonDao {
    private static final List<Field<Signon, ?>> fields = new ArrayList<Field<Signon, ?>>();

    static {
        fields.add(new PkStringField<Signon>("username", "username", 25,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Signon bean) {
                    bean.setUsername(value);
                }

                @Override
                public String getValue(Signon bean) {
                    return bean.getUsername();
                }
            });
        fields.add(new StringField<Signon>("password", "password", false, 25,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Signon bean) {
                    bean.setPassword(value);
                }

                @Override
                public String getValue(Signon bean) {
                    return bean.getPassword();
                }
            });
    }

    public SignonDaoImpl(JdbcHelper jdbcHelper,
        CharsetConverter charsetConverter) {
        super(jdbcHelper, charsetConverter, "signon", fields);
    }

    @Override
    public Signon newIstance() {
        return new SignonImpl();
    }

    protected Tuple newKey(String username) {
        return new Unit<String>(username);
    }

    public Signon getByPrimaryKey(String username) {
        return get(new Unit<String>(username));
    }
}
