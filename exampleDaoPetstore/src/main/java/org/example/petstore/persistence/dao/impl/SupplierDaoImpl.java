package org.example.petstore.persistence.dao.impl;

import it.mengoni.persistence.dao.AbstractRelationDao;
import it.mengoni.persistence.dao.CharsetConverter;
import it.mengoni.persistence.dao.Field;
import it.mengoni.persistence.dao.JdbcHelper;
import it.mengoni.persistence.dao.PkIntegerField;
import it.mengoni.persistence.dao.StringField;

import org.example.petstore.persistence.dao.SupplierDao;
import org.example.petstore.persistence.dto.Supplier;
import org.example.petstore.persistence.dto.impl.SupplierImpl;

import org.javatuples.Tuple;
import org.javatuples.Unit;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;


public class SupplierDaoImpl extends AbstractRelationDao<Supplier>
    implements SupplierDao {
    private static final List<Field<Supplier, ?>> fields = new ArrayList<Field<Supplier, ?>>();

    static {
        fields.add(new PkIntegerField<Supplier>("suppid", "suppid", 10,
                Types.INTEGER) {
                @Override
                public void setValue(Integer value, Supplier bean) {
                    bean.setSuppid(value);
                }

                @Override
                public Integer getValue(Supplier bean) {
                    return bean.getSuppid();
                }
            });
        fields.add(new StringField<Supplier>("name", "name", true, 80,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Supplier bean) {
                    bean.setName(value);
                }

                @Override
                public String getValue(Supplier bean) {
                    return bean.getName();
                }
            });
        fields.add(new StringField<Supplier>("status", "status", false, 2,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Supplier bean) {
                    bean.setStatus(value);
                }

                @Override
                public String getValue(Supplier bean) {
                    return bean.getStatus();
                }
            });
        fields.add(new StringField<Supplier>("addr1", "addr1", true, 80,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Supplier bean) {
                    bean.setAddr1(value);
                }

                @Override
                public String getValue(Supplier bean) {
                    return bean.getAddr1();
                }
            });
        fields.add(new StringField<Supplier>("addr2", "addr2", true, 80,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Supplier bean) {
                    bean.setAddr2(value);
                }

                @Override
                public String getValue(Supplier bean) {
                    return bean.getAddr2();
                }
            });
        fields.add(new StringField<Supplier>("city", "city", true, 80,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Supplier bean) {
                    bean.setCity(value);
                }

                @Override
                public String getValue(Supplier bean) {
                    return bean.getCity();
                }
            });
        fields.add(new StringField<Supplier>("state", "state", true, 80,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Supplier bean) {
                    bean.setState(value);
                }

                @Override
                public String getValue(Supplier bean) {
                    return bean.getState();
                }
            });
        fields.add(new StringField<Supplier>("zip", "zip", true, 5,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Supplier bean) {
                    bean.setZip(value);
                }

                @Override
                public String getValue(Supplier bean) {
                    return bean.getZip();
                }
            });
        fields.add(new StringField<Supplier>("phone", "phone", true, 80,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Supplier bean) {
                    bean.setPhone(value);
                }

                @Override
                public String getValue(Supplier bean) {
                    return bean.getPhone();
                }
            });
    }

    public SupplierDaoImpl(JdbcHelper jdbcHelper,
        CharsetConverter charsetConverter) {
        super(jdbcHelper, charsetConverter, "supplier", fields);
    }

    @Override
    public Supplier newIstance() {
        return new SupplierImpl();
    }

    protected Tuple newKey(Integer suppid) {
        return new Unit<Integer>(suppid);
    }

    public Supplier getByPrimaryKey(Integer suppid) {
        return get(new Unit<Integer>(suppid));
    }
}
