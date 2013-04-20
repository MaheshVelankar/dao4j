package org.example.petstore.persistence.test;

import org.example.petstore.persistence.dao.ProfileDao;
import org.example.petstore.persistence.dto.Profile;

import org.junit.Test;

import java.util.List;


public class TestProfileDao extends AbstractBaseT {
    @Test
    public void Test1() throws Exception {
        ProfileDao dao = getDaoFactory().getProfileDao();
        List<Profile> list = dao.getAll(1, 10);

        for (Profile bean : list)
            System.out.println(bean);
    }
}
