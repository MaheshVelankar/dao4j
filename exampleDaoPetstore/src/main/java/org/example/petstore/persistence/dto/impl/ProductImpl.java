package org.example.petstore.persistence.dto.impl;

import it.mengoni.persistence.dao.ConditionValue;
import it.mengoni.persistence.dto.AbstractPersistentObject;
import it.mengoni.persistence.dto.PoLazyProperties;
import it.mengoni.persistence.dto.PoLazyProperty;
import it.mengoni.persistence.dto.PoProperties;
import it.mengoni.persistence.dto.PoProperty;

import org.example.petstore.persistence.DaoFactory;
import org.example.petstore.persistence.dto.Category;
import org.example.petstore.persistence.dto.Item;
import org.example.petstore.persistence.dto.Product;

import org.javatuples.Tuple;
import org.javatuples.Unit;

import java.util.List;


public class ProductImpl extends AbstractPersistentObject implements Product {
    private static final long serialVersionUID = 1L;
    private String productid;
    private String category;
    private String name;
    private String descn;
    private transient PoProperty<Category> toCategory = new PoLazyProperty<Category>();
    private transient PoProperties<Item> listItem = new PoLazyProperties<Item>();

    public ProductImpl() {
        toCategory.setValue(new CategoryImpl());
        toCategory.unResolve();
    }

    @Override
    public String getProductid() {
        return productid;
    }

    @Override
    public void setProductid(String productid) {
        this.productid = productid;
    }

    @Override
    public String getCategory() {
        return category;
    }

    @Override
    public void setCategory(String category) {
        this.category = category;
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
        return productid;
    }

    public Category getToCategory() {
        if (category == null) {
            return this.toCategory.getValue();
        }

        return toCategory.getValue(DaoFactory.getInstance().getCategoryDao(),
            category);
    }

    public void setToCategory(Category toCategory) {
        this.toCategory.setValue(toCategory);
        this.category = (this.toCategory == null) ? null
                                                  : toCategory.getCatid();
    }

    public List<Item> getListItem() {
        if (productid == null) {
            return null;
        }

        return listItem.getValue(DaoFactory.getInstance().getItemDao(),
            new ConditionValue("productid = ?", productid));
    }

    public void setListItem(List<Item> listItem) {
        this.listItem.setValue(listItem);
    }

    @Override
    protected Tuple newKey() {
        return new Unit<String>(productid);
    }
}
