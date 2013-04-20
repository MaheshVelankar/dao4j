package org.example.petstore.persistence.dao.impl;

import it.mengoni.persistence.dao.AbstractRelationDao;
import it.mengoni.persistence.dao.CharsetConverter;
import it.mengoni.persistence.dao.Field;
import it.mengoni.persistence.dao.FieldImpl;
import it.mengoni.persistence.dao.IntegerField;
import it.mengoni.persistence.dao.JdbcHelper;
import it.mengoni.persistence.dao.PkStringField;
import it.mengoni.persistence.dao.StringField;

import org.example.petstore.persistence.dao.ItemDao;
import org.example.petstore.persistence.dto.Item;
import org.example.petstore.persistence.dto.impl.ItemImpl;

import org.javatuples.Tuple;
import org.javatuples.Unit;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;


public class ItemDaoImpl extends AbstractRelationDao<Item> implements ItemDao {
    private static final List<Field<Item, ?>> fields = new ArrayList<Field<Item, ?>>();

    static {
        fields.add(new PkStringField<Item>("itemid", "itemid", 10, Types.VARCHAR) {
                @Override
                public void setValue(String value, Item bean) {
                    bean.setItemid(value);
                }

                @Override
                public String getValue(Item bean) {
                    return bean.getItemid();
                }
            });
        fields.add(new StringField<Item>("productid", "productid", false, 10,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Item bean) {
                    bean.setProductid(value);
                }

                @Override
                public String getValue(Item bean) {
                    return bean.getProductid();
                }
            });
        fields.add(new FieldImpl<Item, java.math.BigDecimal>("listprice",
                "listprice", true, 10, Types.NUMERIC) {
                @Override
                public void setValue(java.math.BigDecimal value, Item bean) {
                    bean.setListprice(value);
                }

                @Override
                public java.math.BigDecimal getValue(Item bean) {
                    return bean.getListprice();
                }
            });
        fields.add(new FieldImpl<Item, java.math.BigDecimal>("unitcost",
                "unitcost", true, 10, Types.NUMERIC) {
                @Override
                public void setValue(java.math.BigDecimal value, Item bean) {
                    bean.setUnitcost(value);
                }

                @Override
                public java.math.BigDecimal getValue(Item bean) {
                    return bean.getUnitcost();
                }
            });
        fields.add(new IntegerField<Item>("supplier", "supplier", true, 10,
                Types.INTEGER) {
                @Override
                public void setValue(Integer value, Item bean) {
                    bean.setSupplier(value);
                }

                @Override
                public Integer getValue(Item bean) {
                    return bean.getSupplier();
                }
            });
        fields.add(new StringField<Item>("status", "status", true, 2,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Item bean) {
                    bean.setStatus(value);
                }

                @Override
                public String getValue(Item bean) {
                    return bean.getStatus();
                }
            });
        fields.add(new StringField<Item>("attr1", "attr1", true, 80,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Item bean) {
                    bean.setAttr1(value);
                }

                @Override
                public String getValue(Item bean) {
                    return bean.getAttr1();
                }
            });
        fields.add(new StringField<Item>("attr2", "attr2", true, 80,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Item bean) {
                    bean.setAttr2(value);
                }

                @Override
                public String getValue(Item bean) {
                    return bean.getAttr2();
                }
            });
        fields.add(new StringField<Item>("attr3", "attr3", true, 80,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Item bean) {
                    bean.setAttr3(value);
                }

                @Override
                public String getValue(Item bean) {
                    return bean.getAttr3();
                }
            });
        fields.add(new StringField<Item>("attr4", "attr4", true, 80,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Item bean) {
                    bean.setAttr4(value);
                }

                @Override
                public String getValue(Item bean) {
                    return bean.getAttr4();
                }
            });
        fields.add(new StringField<Item>("attr5", "attr5", true, 80,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Item bean) {
                    bean.setAttr5(value);
                }

                @Override
                public String getValue(Item bean) {
                    return bean.getAttr5();
                }
            });
    }

    public ItemDaoImpl(JdbcHelper jdbcHelper, CharsetConverter charsetConverter) {
        super(jdbcHelper, charsetConverter, "item", fields);
    }

    @Override
    public Item newIstance() {
        return new ItemImpl();
    }

    protected Tuple newKey(String itemid) {
        return new Unit<String>(itemid);
    }

    public Item getByPrimaryKey(String itemid) {
        return get(new Unit<String>(itemid));
    }
}
