package org.example.petstore.persistence.dao;

import it.mengoni.persistence.dao.Dao;

import org.example.petstore.persistence.dto.Profile;


public interface ProfileDao extends Dao<Profile> {
    public Profile getByPrimaryKey(String userid);
}
