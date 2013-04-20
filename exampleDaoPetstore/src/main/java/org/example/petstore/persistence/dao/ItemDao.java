package org.example.petstore.persistence.dao;

import it.mengoni.persistence.dao.Dao;

import org.example.petstore.persistence.dto.Item;


public interface ItemDao extends Dao<Item> {
    public Item getByPrimaryKey(String itemid);
}
