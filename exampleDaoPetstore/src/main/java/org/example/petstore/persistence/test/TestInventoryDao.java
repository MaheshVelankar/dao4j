package org.example.petstore.persistence.test;

import org.example.petstore.persistence.dao.InventoryDao;
import org.example.petstore.persistence.dto.Inventory;

import org.junit.Test;

import java.util.List;


public class TestInventoryDao extends AbstractBaseT {
    @Test
    public void Test1() throws Exception {
        InventoryDao dao = getDaoFactory().getInventoryDao();
        List<Inventory> list = dao.getAll(1, 10);

        for (Inventory bean : list)
            System.out.println(bean);
    }
}
