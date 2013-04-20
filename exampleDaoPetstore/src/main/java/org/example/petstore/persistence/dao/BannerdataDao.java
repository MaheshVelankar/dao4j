package org.example.petstore.persistence.dao;

import it.mengoni.persistence.dao.Dao;

import org.example.petstore.persistence.dto.Bannerdata;


public interface BannerdataDao extends Dao<Bannerdata> {
    public Bannerdata getByPrimaryKey(String favcategory);
}
