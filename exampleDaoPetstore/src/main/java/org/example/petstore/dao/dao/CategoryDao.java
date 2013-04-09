package org.example.petstore.dao.dao;
import org.example.petstore.dao.dto.Category;
import it.mengoni.persistence.dao.Dao;
public interface CategoryDao extends Dao<Category> {
public Category getByPrimaryKey(String catid);
}
