package org.example.petstore.persistence.dao;

import it.mengoni.persistence.dao.Dao;

import org.example.petstore.persistence.dto.Lineitem;


public interface LineitemDao extends Dao<Lineitem> {
    public Lineitem getByPrimaryKey(Integer orderid, Integer linenum);
}
