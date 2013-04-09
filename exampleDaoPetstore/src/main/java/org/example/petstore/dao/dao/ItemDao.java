package org.example.petstore.dao.dao;
import org.example.petstore.dao.dto.Item;
import it.mengoni.persistence.dao.Dao;
public interface ItemDao extends Dao<Item> {
public Item getByPrimaryKey(String itemid);
}
