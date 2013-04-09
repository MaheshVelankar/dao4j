package org.example.petstore.dao.dao;
import it.mengoni.persistence.dao.Dao;
import org.example.petstore.dao.dto.Profile;
public interface ProfileDao extends Dao<Profile> {
public Profile getByPrimaryKey(String userid);
}
