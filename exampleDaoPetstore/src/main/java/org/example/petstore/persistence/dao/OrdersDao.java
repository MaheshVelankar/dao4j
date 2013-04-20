package org.example.petstore.persistence.dao;

import it.mengoni.persistence.dao.Dao;

import org.example.petstore.persistence.dto.Orders;


public interface OrdersDao extends Dao<Orders> {
    public Orders getByPrimaryKey(Integer orderid);
}
