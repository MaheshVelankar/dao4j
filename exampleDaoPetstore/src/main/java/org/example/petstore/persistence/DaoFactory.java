package org.example.petstore.persistence;

import it.mengoni.persistence.dao.AbstractDaoFactory;

import org.example.petstore.persistence.dao.AccountDao;
import org.example.petstore.persistence.dao.BannerdataDao;
import org.example.petstore.persistence.dao.CategoryDao;
import org.example.petstore.persistence.dao.InventoryDao;
import org.example.petstore.persistence.dao.ItemDao;
import org.example.petstore.persistence.dao.LineitemDao;
import org.example.petstore.persistence.dao.OrdersDao;
import org.example.petstore.persistence.dao.OrderstatusDao;
import org.example.petstore.persistence.dao.ProductDao;
import org.example.petstore.persistence.dao.ProfileDao;
import org.example.petstore.persistence.dao.SequenceDao;
import org.example.petstore.persistence.dao.SignonDao;
import org.example.petstore.persistence.dao.SupplierDao;
import org.example.petstore.persistence.dao.impl.AccountDaoImpl;
import org.example.petstore.persistence.dao.impl.BannerdataDaoImpl;
import org.example.petstore.persistence.dao.impl.CategoryDaoImpl;
import org.example.petstore.persistence.dao.impl.InventoryDaoImpl;
import org.example.petstore.persistence.dao.impl.ItemDaoImpl;
import org.example.petstore.persistence.dao.impl.LineitemDaoImpl;
import org.example.petstore.persistence.dao.impl.OrdersDaoImpl;
import org.example.petstore.persistence.dao.impl.OrderstatusDaoImpl;
import org.example.petstore.persistence.dao.impl.ProductDaoImpl;
import org.example.petstore.persistence.dao.impl.ProfileDaoImpl;
import org.example.petstore.persistence.dao.impl.SequenceDaoImpl;
import org.example.petstore.persistence.dao.impl.SignonDaoImpl;
import org.example.petstore.persistence.dao.impl.SupplierDaoImpl;


public class DaoFactory extends AbstractDaoFactory {
    private static DaoFactory instance;

    public static DaoFactory getInstance() {
        return instance;
    }

    public void init() {
        DaoFactory.instance = this;
    }

    public AccountDao getAccountDao() {
        return new AccountDaoImpl(getHelper(), getCharsetConverter());
    }

    public BannerdataDao getBannerdataDao() {
        return new BannerdataDaoImpl(getHelper(), getCharsetConverter());
    }

    public CategoryDao getCategoryDao() {
        return new CategoryDaoImpl(getHelper(), getCharsetConverter());
    }

    public InventoryDao getInventoryDao() {
        return new InventoryDaoImpl(getHelper(), getCharsetConverter());
    }

    public ItemDao getItemDao() {
        return new ItemDaoImpl(getHelper(), getCharsetConverter());
    }

    public LineitemDao getLineitemDao() {
        return new LineitemDaoImpl(getHelper(), getCharsetConverter());
    }

    public OrdersDao getOrdersDao() {
        return new OrdersDaoImpl(getHelper(), getCharsetConverter());
    }

    public OrderstatusDao getOrderstatusDao() {
        return new OrderstatusDaoImpl(getHelper(), getCharsetConverter());
    }

    public ProductDao getProductDao() {
        return new ProductDaoImpl(getHelper(), getCharsetConverter());
    }

    public ProfileDao getProfileDao() {
        return new ProfileDaoImpl(getHelper(), getCharsetConverter());
    }

    public SequenceDao getSequenceDao() {
        return new SequenceDaoImpl(getHelper(), getCharsetConverter());
    }

    public SignonDao getSignonDao() {
        return new SignonDaoImpl(getHelper(), getCharsetConverter());
    }

    public SupplierDao getSupplierDao() {
        return new SupplierDaoImpl(getHelper(), getCharsetConverter());
    }
}
