package org.example.petstore.persistence.dto;

import it.mengoni.persistence.dto.PersistentObject;

import java.math.BigDecimal;


public interface Item extends PersistentObject {
    public String getItemid();

    public void setItemid(String itemid);

    public String getProductid();

    public void setProductid(String productid);

    public BigDecimal getListprice();

    public void setListprice(BigDecimal listprice);

    public BigDecimal getUnitcost();

    public void setUnitcost(BigDecimal unitcost);

    public Integer getSupplier();

    public void setSupplier(Integer supplier);

    public String getStatus();

    public void setStatus(String status);

    public String getAttr1();

    public void setAttr1(String attr1);

    public String getAttr2();

    public void setAttr2(String attr2);

    public String getAttr3();

    public void setAttr3(String attr3);

    public String getAttr4();

    public void setAttr4(String attr4);

    public String getAttr5();

    public void setAttr5(String attr5);

    public Product getToProduct();

    public void setToProduct(Product toProduct);

    public Supplier getToSupplier();

    public void setToSupplier(Supplier toSupplier);
}
