package org.example.petstore.dao.dao;
import it.mengoni.persistence.dao.Dao;
import org.example.petstore.dao.dto.Supplier;
public interface SupplierDao extends Dao<Supplier> {
public Supplier getByPrimaryKey(Integer suppid);
}
