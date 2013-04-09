package org.example.petstore.dao.dao;
import org.example.petstore.dao.dto.Bannerdata;
import it.mengoni.persistence.dao.Dao;
public interface BannerdataDao extends Dao<Bannerdata> {
public Bannerdata getByPrimaryKey(String favcategory);
}
