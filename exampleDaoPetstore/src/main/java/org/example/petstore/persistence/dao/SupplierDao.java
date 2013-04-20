package org.example.petstore.persistence.dao;

import it.mengoni.persistence.dao.Dao;

import org.example.petstore.persistence.dto.Supplier;


public interface SupplierDao extends Dao<Supplier> {
    public Supplier getByPrimaryKey(Integer suppid);
}
