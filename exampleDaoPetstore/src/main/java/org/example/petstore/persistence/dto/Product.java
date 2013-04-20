package org.example.petstore.persistence.dto;

import it.mengoni.persistence.dto.PersistentObject;

import java.util.List;


public interface Product extends PersistentObject {
    public String getProductid();

    public void setProductid(String productid);

    public String getCategory();

    public void setCategory(String category);

    public String getName();

    public void setName(String name);

    public String getDescn();

    public void setDescn(String descn);

    public Category getToCategory();

    public void setToCategory(Category toCategory);

    public List<Item> getListItem();

    public void setListItem(List<Item> toItem);
}
