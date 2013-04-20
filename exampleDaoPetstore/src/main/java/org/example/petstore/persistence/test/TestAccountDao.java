package org.example.petstore.persistence.test;

import org.example.petstore.persistence.dao.AccountDao;
import org.example.petstore.persistence.dto.Account;

import org.junit.Test;

import java.util.List;


public class TestAccountDao extends AbstractBaseT {
    @Test
    public void Test1() throws Exception {
        AccountDao dao = getDaoFactory().getAccountDao();
        List<Account> list = dao.getAll(1, 10);

        for (Account bean : list)
            System.out.println(bean);
    }
}
