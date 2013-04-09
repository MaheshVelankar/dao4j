package org.example.petstore.dao.dao;
import org.example.petstore.dao.dto.Inventory;
import it.mengoni.persistence.dao.Dao;
public interface InventoryDao extends Dao<Inventory> {
public Inventory getByPrimaryKey(String itemid);
}
