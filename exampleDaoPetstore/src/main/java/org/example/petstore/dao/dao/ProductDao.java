package org.example.petstore.dao.dao;
import org.example.petstore.dao.dto.Product;
import it.mengoni.persistence.dao.Dao;
public interface ProductDao extends Dao<Product> {
public Product getByPrimaryKey(String productid);
}
