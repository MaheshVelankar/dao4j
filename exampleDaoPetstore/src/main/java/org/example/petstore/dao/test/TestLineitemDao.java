package org.example.petstore.dao.test;
import org.example.petstore.dao.dto.Lineitem;
import java.util.List;
import org.example.petstore.dao.dao.LineitemDao;
import org.junit.Test;
public class TestLineitemDao extends AbstractBaseT {  

	@Test
	public void Test1() throws Exception {     
		LineitemDao dao = getDaoFactory().getLineitemDao();
		List<Lineitem> list = dao.getAll(1,10);
		for (Lineitem bean : list)
			System.out.println(bean);
	}

}
