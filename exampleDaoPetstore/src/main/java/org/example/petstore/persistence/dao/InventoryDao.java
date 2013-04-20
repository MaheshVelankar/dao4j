package org.example.petstore.persistence.dao;

import it.mengoni.persistence.dao.Dao;

import org.example.petstore.persistence.dto.Inventory;


public interface InventoryDao extends Dao<Inventory> {
    public Inventory getByPrimaryKey(String itemid);
}
