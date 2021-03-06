package org.example.petstore.persistence.dao.impl;

import it.mengoni.persistence.dao.AbstractRelationDao;
import it.mengoni.persistence.dao.CharsetConverter;
import it.mengoni.persistence.dao.Field;
import it.mengoni.persistence.dao.FieldImpl;
import it.mengoni.persistence.dao.JdbcHelper;
import it.mengoni.persistence.dao.PkStringField;
import it.mengoni.persistence.dao.StringField;

import org.example.petstore.persistence.dao.ProfileDao;
import org.example.petstore.persistence.dto.Profile;
import org.example.petstore.persistence.dto.impl.ProfileImpl;

import org.javatuples.Tuple;
import org.javatuples.Unit;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;


public class ProfileDaoImpl extends AbstractRelationDao<Profile>
    implements ProfileDao {
    private static final List<Field<Profile, ?>> fields = new ArrayList<Field<Profile, ?>>();

    static {
        fields.add(new PkStringField<Profile>("userid", "userid", 80,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Profile bean) {
                    bean.setUserid(value);
                }

                @Override
                public String getValue(Profile bean) {
                    return bean.getUserid();
                }
            });
        fields.add(new StringField<Profile>("langpref", "langpref", false, 80,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Profile bean) {
                    bean.setLangpref(value);
                }

                @Override
                public String getValue(Profile bean) {
                    return bean.getLangpref();
                }
            });
        fields.add(new StringField<Profile>("favcategory", "favcategory", true,
                30, Types.VARCHAR) {
                @Override
                public void setValue(String value, Profile bean) {
                    bean.setFavcategory(value);
                }

                @Override
                public String getValue(Profile bean) {
                    return bean.getFavcategory();
                }
            });
        fields.add(new FieldImpl<Profile, Boolean>("mylistopt", "mylistopt",
                true, 1, Types.BIT) {
                @Override
                public void setValue(Boolean value, Profile bean) {
                    bean.setMylistopt(value);
                }

                @Override
                public Boolean getValue(Profile bean) {
                    return bean.getMylistopt();
                }
            });
        fields.add(new FieldImpl<Profile, Boolean>("banneropt", "banneropt",
                true, 1, Types.BIT) {
                @Override
                public void setValue(Boolean value, Profile bean) {
                    bean.setBanneropt(value);
                }

                @Override
                public Boolean getValue(Profile bean) {
                    return bean.getBanneropt();
                }
            });
    }

    public ProfileDaoImpl(JdbcHelper jdbcHelper,
        CharsetConverter charsetConverter) {
        super(jdbcHelper, charsetConverter, "profile", fields);
    }

    @Override
    public Profile newIstance() {
        return new ProfileImpl();
    }

    protected Tuple newKey(String userid) {
        return new Unit<String>(userid);
    }

    public Profile getByPrimaryKey(String userid) {
        return get(new Unit<String>(userid));
    }
}
