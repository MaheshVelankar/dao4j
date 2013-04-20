package org.example.petstore.persistence.dto.impl;

import it.mengoni.persistence.dao.ConditionValue;
import it.mengoni.persistence.dto.AbstractPersistentObject;
import it.mengoni.persistence.dto.PoLazyProperties;
import it.mengoni.persistence.dto.PoProperties;

import org.example.petstore.persistence.DaoFactory;
import org.example.petstore.persistence.dto.Category;
import org.example.petstore.persistence.dto.Product;

import org.javatuples.Tuple;
import org.javatuples.Unit;

import java.util.List;


public class CategoryImpl extends AbstractPersistentObject implements Category {
    private static final long serialVersionUID = 1L;
    private String catid;
    private String name;
    private String descn;
    private transient PoProperties<Product> listProduct = new PoLazyProperties<Product>();

    public CategoryImpl() {
    }

    @Override
    public String getCatid() {
        return catid;
    }

    @Override
    public void setCatid(String catid) {
        this.catid = catid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescn() {
        return descn;
    }

    @Override
    public void setDescn(String descn) {
        this.descn = descn;
    }

    @Override
    public String getDisplayLabel() {
        return catid;
    }

    public List<Product> getListProduct() {
        if (catid == null) {
            return null;
        }

        return listProduct.getValue(DaoFactory.getInstance().getProductDao(),
            new ConditionValue("category = ?", catid));
    }

    public void setListProduct(List<Product> listProduct) {
        this.listProduct.setValue(listProduct);
    }

    @Override
    protected Tuple newKey() {
        return new Unit<String>(catid);
    }
}
