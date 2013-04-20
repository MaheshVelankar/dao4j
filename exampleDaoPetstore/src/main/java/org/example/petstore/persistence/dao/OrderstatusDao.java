package org.example.petstore.persistence.dao;

import it.mengoni.persistence.dao.Dao;

import org.example.petstore.persistence.dto.Orderstatus;


public interface OrderstatusDao extends Dao<Orderstatus> {
    public Orderstatus getByPrimaryKey(Integer orderid, Integer linenum);
}
