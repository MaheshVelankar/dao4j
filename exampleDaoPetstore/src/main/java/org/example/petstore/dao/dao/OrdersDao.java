package org.example.petstore.dao.dao;
import org.example.petstore.dao.dto.Orders;
import it.mengoni.persistence.dao.Dao;
public interface OrdersDao extends Dao<Orders> {
public Orders getByPrimaryKey(Integer orderid);
}
