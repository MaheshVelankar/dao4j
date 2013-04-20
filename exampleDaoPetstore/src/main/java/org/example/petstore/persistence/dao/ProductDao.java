package org.example.petstore.persistence.dao;

import it.mengoni.persistence.dao.Dao;

import org.example.petstore.persistence.dto.Product;


public interface ProductDao extends Dao<Product> {
    public Product getByPrimaryKey(String productid);
}
