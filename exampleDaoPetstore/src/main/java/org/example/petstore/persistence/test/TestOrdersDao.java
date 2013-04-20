package org.example.petstore.persistence.test;

import org.example.petstore.persistence.dao.OrdersDao;
import org.example.petstore.persistence.dto.Orders;

import org.junit.Test;

import java.util.List;


public class TestOrdersDao extends AbstractBaseT {
    @Test
    public void Test1() throws Exception {
        OrdersDao dao = getDaoFactory().getOrdersDao();
        List<Orders> list = dao.getAll(1, 10);

        for (Orders bean : list)
            System.out.println(bean);
    }
}
