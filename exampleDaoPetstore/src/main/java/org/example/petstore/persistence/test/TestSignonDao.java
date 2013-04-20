package org.example.petstore.persistence.test;

import org.example.petstore.persistence.dao.SignonDao;
import org.example.petstore.persistence.dto.Signon;

import org.junit.Test;

import java.util.List;


public class TestSignonDao extends AbstractBaseT {
    @Test
    public void Test1() throws Exception {
        SignonDao dao = getDaoFactory().getSignonDao();
        List<Signon> list = dao.getAll(1, 10);

        for (Signon bean : list)
            System.out.println(bean);
    }
}
