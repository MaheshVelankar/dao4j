package org.example.petstore.persistence.test;

import org.example.petstore.persistence.dao.OrderstatusDao;
import org.example.petstore.persistence.dto.Orderstatus;

import org.junit.Test;

import java.util.List;


public class TestOrderstatusDao extends AbstractBaseT {
    @Test
    public void Test1() throws Exception {
        OrderstatusDao dao = getDaoFactory().getOrderstatusDao();
        List<Orderstatus> list = dao.getAll(1, 10);

        for (Orderstatus bean : list)
            System.out.println(bean);
    }
}
