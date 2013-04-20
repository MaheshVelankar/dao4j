package org.example.petstore.persistence.test;

import org.example.petstore.persistence.dao.LineitemDao;
import org.example.petstore.persistence.dto.Lineitem;

import org.junit.Test;

import java.util.List;


public class TestLineitemDao extends AbstractBaseT {
    @Test
    public void Test1() throws Exception {
        LineitemDao dao = getDaoFactory().getLineitemDao();
        List<Lineitem> list = dao.getAll(1, 10);

        for (Lineitem bean : list)
            System.out.println(bean);
    }
}
