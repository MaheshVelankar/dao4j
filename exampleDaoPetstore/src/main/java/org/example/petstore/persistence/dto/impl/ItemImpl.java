package org.example.petstore.persistence.dto.impl;

import it.mengoni.persistence.dto.AbstractPersistentObject;
import it.mengoni.persistence.dto.PoLazyProperty;
import it.mengoni.persistence.dto.PoProperty;

import org.example.petstore.persistence.DaoFactory;
import org.example.petstore.persistence.dto.Item;
import org.example.petstore.persistence.dto.Product;
import org.example.petstore.persistence.dto.Supplier;

import org.javatuples.Tuple;
import org.javatuples.Unit;

import java.math.BigDecimal;


public class ItemImpl extends AbstractPersistentObject implements Item {
    private static final long serialVersionUID = 1L;
    private String itemid;
    private String productid;
    private BigDecimal listprice;
    private BigDecimal unitcost;
    private Integer supplier;
    private String status;
    private String attr1;
    private String attr2;
    private String attr3;
    private String attr4;
    private String attr5;
    private transient PoProperty<Product> toProduct = new PoLazyProperty<Product>();
    private transient PoProperty<Supplier> toSupplier = new PoLazyProperty<Supplier>();

    public ItemImpl() {
        toProduct.setValue(new ProductImpl());
        toProduct.unResolve();
        toSupplier.setValue(new SupplierImpl());
        toSupplier.unResolve();
    }

    @Override
    public String getItemid() {
        return itemid;
    }

    @Override
    public void setItemid(String itemid) {
        this.itemid = itemid;
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
    public BigDecimal getListprice() {
        return listprice;
    }

    @Override
    public void setListprice(BigDecimal listprice) {
        this.listprice = listprice;
    }

    @Override
    public BigDecimal getUnitcost() {
        return unitcost;
    }

    @Override
    public void setUnitcost(BigDecimal unitcost) {
        this.unitcost = unitcost;
    }

    @Override
    public Integer getSupplier() {
        return supplier;
    }

    @Override
    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getAttr1() {
        return attr1;
    }

    @Override
    public void setAttr1(String attr1) {
        this.attr1 = attr1;
    }

    @Override
    public String getAttr2() {
        return attr2;
    }

    @Override
    public void setAttr2(String attr2) {
        this.attr2 = attr2;
    }

    @Override
    public String getAttr3() {
        return attr3;
    }

    @Override
    public void setAttr3(String attr3) {
        this.attr3 = attr3;
    }

    @Override
    public String getAttr4() {
        return attr4;
    }

    @Override
    public void setAttr4(String attr4) {
        this.attr4 = attr4;
    }

    @Override
    public String getAttr5() {
        return attr5;
    }

    @Override
    public void setAttr5(String attr5) {
        this.attr5 = attr5;
    }

    @Override
    public String getDisplayLabel() {
        return itemid;
    }

    public Product getToProduct() {
        if (productid == null) {
            return this.toProduct.getValue();
        }

        return toProduct.getValue(DaoFactory.getInstance().getProductDao(),
            productid);
    }

    public void setToProduct(Product toProduct) {
        this.toProduct.setValue(toProduct);
        this.productid = (this.toProduct == null) ? null
                                                  : toProduct.getProductid();
    }

    public Supplier getToSupplier() {
        if (supplier == null) {
            return this.toSupplier.getValue();
        }

        return toSupplier.getValue(DaoFactory.getInstance().getSupplierDao(),
            supplier);
    }

    public void setToSupplier(Supplier toSupplier) {
        this.toSupplier.setValue(toSupplier);
        this.supplier = (this.toSupplier == null) ? null
                                                  : toSupplier.getSuppid();
    }

    @Override
    protected Tuple newKey() {
        return new Unit<String>(itemid);
    }
}
