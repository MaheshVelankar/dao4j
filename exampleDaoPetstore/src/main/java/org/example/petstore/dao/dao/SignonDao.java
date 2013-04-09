package org.example.petstore.dao.dao;
import it.mengoni.persistence.dao.Dao;
import org.example.petstore.dao.dto.Signon;
public interface SignonDao extends Dao<Signon> {
public Signon getByPrimaryKey(String username);
}
