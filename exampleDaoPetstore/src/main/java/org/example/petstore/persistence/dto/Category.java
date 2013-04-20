package org.example.petstore.persistence.dto;

import it.mengoni.persistence.dto.PersistentObject;

import java.util.List;


public interface Category extends PersistentObject {
    public String getCatid();

    public void setCatid(String catid);

    public String getName();

    public void setName(String name);

    public String getDescn();

    public void setDescn(String descn);

    public List<Product> getListProduct();

    public void setListProduct(List<Product> toProduct);
}
