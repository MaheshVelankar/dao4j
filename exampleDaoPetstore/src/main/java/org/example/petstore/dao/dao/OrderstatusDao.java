package org.example.petstore.dao.dao;
import org.example.petstore.dao.dto.Orderstatus;
import it.mengoni.persistence.dao.Dao;
public interface OrderstatusDao extends Dao<Orderstatus> {
public Orderstatus getByPrimaryKey(Integer orderid, Integer linenum);
}
