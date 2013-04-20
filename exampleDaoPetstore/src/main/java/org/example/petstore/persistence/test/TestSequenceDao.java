package org.example.petstore.persistence.test;

import org.example.petstore.persistence.dao.SequenceDao;
import org.example.petstore.persistence.dto.Sequence;

import org.junit.Test;

import java.util.List;


public class TestSequenceDao extends AbstractBaseT {
    @Test
    public void Test1() throws Exception {
        SequenceDao dao = getDaoFactory().getSequenceDao();
        List<Sequence> list = dao.getAll(1, 10);

        for (Sequence bean : list)
            System.out.println(bean);
    }
}
