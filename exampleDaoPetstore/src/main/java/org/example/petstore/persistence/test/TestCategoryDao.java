package org.example.petstore.persistence.test;

import org.example.petstore.persistence.dao.CategoryDao;
import org.example.petstore.persistence.dto.Category;

import org.junit.Test;

import java.util.List;


public class TestCategoryDao extends AbstractBaseT {
    @Test
    public void Test1() throws Exception {
        CategoryDao dao = getDaoFactory().getCategoryDao();
        List<Category> list = dao.getAll(1, 10);

        for (Category bean : list)
            System.out.println(bean);
    }
}
