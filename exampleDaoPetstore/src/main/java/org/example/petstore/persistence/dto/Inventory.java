package org.example.petstore.persistence.dto;

import it.mengoni.persistence.dto.PersistentObject;


public interface Inventory extends PersistentObject {
    public String getItemid();

    public void setItemid(String itemid);

    public Integer getQty();

    public void setQty(Integer qty);
}
