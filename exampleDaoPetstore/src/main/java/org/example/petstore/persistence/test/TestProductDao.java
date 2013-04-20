package org.example.petstore.persistence.test;

import org.example.petstore.persistence.dao.ProductDao;
import org.example.petstore.persistence.dto.Product;

import org.junit.Test;

import java.util.List;


public class TestProductDao extends AbstractBaseT {
    @Test
    public void Test1() throws Exception {
        ProductDao dao = getDaoFactory().getProductDao();
        List<Product> list = dao.getAll(1, 10);

        for (Product bean : list)
            System.out.println(bean);
    }
}
