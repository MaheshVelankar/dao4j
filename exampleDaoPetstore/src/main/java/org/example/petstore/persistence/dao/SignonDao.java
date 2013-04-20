package org.example.petstore.persistence.dao;

import it.mengoni.persistence.dao.Dao;

import org.example.petstore.persistence.dto.Signon;


public interface SignonDao extends Dao<Signon> {
    public Signon getByPrimaryKey(String username);
}
