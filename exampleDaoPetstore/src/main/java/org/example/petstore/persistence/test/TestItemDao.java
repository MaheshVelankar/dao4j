package org.example.petstore.persistence.test;

import org.example.petstore.persistence.dao.ItemDao;
import org.example.petstore.persistence.dto.Item;

import org.junit.Test;

import java.util.List;


public class TestItemDao extends AbstractBaseT {
    @Test
    public void Test1() throws Exception {
        ItemDao dao = getDaoFactory().getItemDao();
        List<Item> list = dao.getAll(1, 10);

        for (Item bean : list)
            System.out.println(bean);
    }
}
