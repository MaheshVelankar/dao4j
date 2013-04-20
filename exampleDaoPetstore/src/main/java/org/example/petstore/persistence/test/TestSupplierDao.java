package org.example.petstore.persistence.test;

import org.example.petstore.persistence.dao.SupplierDao;
import org.example.petstore.persistence.dto.Supplier;

import org.junit.Test;

import java.util.List;


public class TestSupplierDao extends AbstractBaseT {
    @Test
    public void Test1() throws Exception {
        SupplierDao dao = getDaoFactory().getSupplierDao();
        List<Supplier> list = dao.getAll(1, 10);

        for (Supplier bean : list)
            System.out.println(bean);
    }
}
