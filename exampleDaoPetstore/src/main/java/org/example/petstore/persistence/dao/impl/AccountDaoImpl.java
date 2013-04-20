package org.example.petstore.persistence.dao.impl;

import it.mengoni.persistence.dao.AbstractRelationDao;
import it.mengoni.persistence.dao.CharsetConverter;
import it.mengoni.persistence.dao.Field;
import it.mengoni.persistence.dao.JdbcHelper;
import it.mengoni.persistence.dao.PkStringField;
import it.mengoni.persistence.dao.StringField;

import org.example.petstore.persistence.dao.AccountDao;
import org.example.petstore.persistence.dto.Account;
import org.example.petstore.persistence.dto.impl.AccountImpl;

import org.javatuples.Tuple;
import org.javatuples.Unit;

import java.sql.Types;

import java.util.ArrayList;
import java.util.List;


public class AccountDaoImpl extends AbstractRelationDao<Account>
    implements AccountDao {
    private static final List<Field<Account, ?>> fields = new ArrayList<Field<Account, ?>>();

    static {
        fields.add(new PkStringField<Account>("userid", "userid", 80,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Account bean) {
                    bean.setUserid(value);
                }

                @Override
                public String getValue(Account bean) {
                    return bean.getUserid();
                }
            });
        fields.add(new StringField<Account>("email", "email", false, 80,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Account bean) {
                    bean.setEmail(value);
                }

                @Override
                public String getValue(Account bean) {
                    return bean.getEmail();
                }
            });
        fields.add(new StringField<Account>("firstname", "firstname", false,
                80, Types.VARCHAR) {
                @Override
                public void setValue(String value, Account bean) {
                    bean.setFirstname(value);
                }

                @Override
                public String getValue(Account bean) {
                    return bean.getFirstname();
                }
            });
        fields.add(new StringField<Account>("lastname", "lastname", false, 80,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Account bean) {
                    bean.setLastname(value);
                }

                @Override
                public String getValue(Account bean) {
                    return bean.getLastname();
                }
            });
        fields.add(new StringField<Account>("status", "status", true, 2,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Account bean) {
                    bean.setStatus(value);
                }

                @Override
                public String getValue(Account bean) {
                    return bean.getStatus();
                }
            });
        fields.add(new StringField<Account>("addr1", "addr1", false, 80,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Account bean) {
                    bean.setAddr1(value);
                }

                @Override
                public String getValue(Account bean) {
                    return bean.getAddr1();
                }
            });
        fields.add(new StringField<Account>("addr2", "addr2", true, 40,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Account bean) {
                    bean.setAddr2(value);
                }

                @Override
                public String getValue(Account bean) {
                    return bean.getAddr2();
                }
            });
        fields.add(new StringField<Account>("city", "city", false, 80,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Account bean) {
                    bean.setCity(value);
                }

                @Override
                public String getValue(Account bean) {
                    return bean.getCity();
                }
            });
        fields.add(new StringField<Account>("state", "state", false, 80,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Account bean) {
                    bean.setState(value);
                }

                @Override
                public String getValue(Account bean) {
                    return bean.getState();
                }
            });
        fields.add(new StringField<Account>("zip", "zip", false, 20,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Account bean) {
                    bean.setZip(value);
                }

                @Override
                public String getValue(Account bean) {
                    return bean.getZip();
                }
            });
        fields.add(new StringField<Account>("country", "country", false, 20,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Account bean) {
                    bean.setCountry(value);
                }

                @Override
                public String getValue(Account bean) {
                    return bean.getCountry();
                }
            });
        fields.add(new StringField<Account>("phone", "phone", false, 80,
                Types.VARCHAR) {
                @Override
                public void setValue(String value, Account bean) {
                    bean.setPhone(value);
                }

                @Override
                public String getValue(Account bean) {
                    return bean.getPhone();
                }
            });
    }

    public AccountDaoImpl(JdbcHelper jdbcHelper,
        CharsetConverter charsetConverter) {
        super(jdbcHelper, charsetConverter, "account", fields);
    }

    @Override
    public Account newIstance() {
        return new AccountImpl();
    }

    protected Tuple newKey(String userid) {
        return new Unit<String>(userid);
    }

    public Account getByPrimaryKey(String userid) {
        return get(new Unit<String>(userid));
    }
}
