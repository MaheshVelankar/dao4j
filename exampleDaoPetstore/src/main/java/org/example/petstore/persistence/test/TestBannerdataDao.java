package org.example.petstore.persistence.test;

import org.example.petstore.persistence.dao.BannerdataDao;
import org.example.petstore.persistence.dto.Bannerdata;

import org.junit.Test;

import java.util.List;


public class TestBannerdataDao extends AbstractBaseT {
    @Test
    public void Test1() throws Exception {
        BannerdataDao dao = getDaoFactory().getBannerdataDao();
        List<Bannerdata> list = dao.getAll(1, 10);

        for (Bannerdata bean : list)
            System.out.println(bean);
    }
}
