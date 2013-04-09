package org.example.petstore.dao.test;
import org.example.petstore.dao.dao.SequenceDao;
import org.example.petstore.dao.dto.Sequence;
import java.util.List;
import org.junit.Test;
public class TestSequenceDao extends AbstractBaseT {  

	@Test
	public void Test1() throws Exception {     
		SequenceDao dao = getDaoFactory().getSequenceDao();
		List<Sequence> list = dao.getAll(1,10);
		for (Sequence bean : list)
			System.out.println(bean);
	}

}
