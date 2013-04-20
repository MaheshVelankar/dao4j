package org.example.petstore.persistence.dao.impl;

import it.mengoni.persistence.dao.AbstractRelationDao;
import it.mengoni.persistence.dao.CharsetConverter;
import it.mengoni.persistence.dao.Field;
import it.mengoni.persistence.dao.JdbcHelper;
import it.mengoni.persistence.dao.PkStringField;
import it.mengoni.persistence.dao.StringField;

import org.example.petstore.persistence.dao.BannerdataDao;
import org.example.petstore.persistence.dto.Bannerdata;
import org.example.petstore.persistence.dto.impl.BannerdataImpl;

import org.javatuples.Tuple;
import org.javatuples.Unit;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;


public class BannerdataDaoImpl extends AbstractRelationDao<Bannerdata>
    implements BannerdataDao {
    private static final List<Field<Bannerdata, ?>> fields = new ArrayList<Field<Bannerdata, ?>>();

    static {
        fields.add(new PkStringField<Bannerdata>("favcategory", "favcategory",
                80, Types.VARCHAR) {
                @Override
                public void setValue(String value, Bannerdata bean) {
                    bean.setFavcategory(value);
                }

                @Override
                public String getValue(Bannerdata bean) {
                    return bean.getFavcategory();
                }
            });
        fields.add(new StringField<Bannerdata>("bannername", "bannername",
                true, 255, Types.VARCHAR) {
                @Override
                public void setValue(String value, Bannerdata bean) {
                    bean.setBannername(value);
                }

                @Override
                public String getValue(Bannerdata bean) {
                    return bean.getBannername();
                }
            });
    }

    public BannerdataDaoImpl(JdbcHelper jdbcHelper,
        CharsetConverter charsetConverter) {
        super(jdbcHelper, charsetConverter, "bannerdata", fields);
    }

    @Override
    public Bannerdata newIstance() {
        return new BannerdataImpl();
    }

    protected Tuple newKey(String favcategory) {
        return new Unit<String>(favcategory);
    }

    public Bannerdata getByPrimaryKey(String favcategory) {
        return get(new Unit<String>(favcategory));
    }
}
