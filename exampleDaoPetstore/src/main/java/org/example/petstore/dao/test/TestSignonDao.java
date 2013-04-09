package org.example.petstore.dao.test;
import java.util.List;
import org.example.petstore.dao.dao.SignonDao;
import org.junit.Test;
import org.example.petstore.dao.dto.Signon;
public class TestSignonDao extends AbstractBaseT {  

	@Test
	public void Test1() throws Exception {     
		SignonDao dao = getDaoFactory().getSignonDao();
		List<Signon> list = dao.getAll(1,10);
		for (Signon bean : list)
			System.out.println(bean);
	}

}
