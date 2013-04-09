package org.example.petstore.dao.dao;
import org.example.petstore.dao.dto.Lineitem;
import it.mengoni.persistence.dao.Dao;
public interface LineitemDao extends Dao<Lineitem> {
public Lineitem getByPrimaryKey(Integer orderid, Integer linenum);
}
