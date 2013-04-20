package org.example.petstore.persistence.dao;

import it.mengoni.persistence.dao.Dao;

import org.example.petstore.persistence.dto.Category;


public interface CategoryDao extends Dao<Category> {
    public Category getByPrimaryKey(String catid);
}
