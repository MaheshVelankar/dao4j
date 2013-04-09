package org.example.petstore.dao.test;
import org.example.petstore.dao.dto.Orderstatus;
import java.util.List;
import org.example.petstore.dao.dao.OrderstatusDao;
import org.junit.Test;
public class TestOrderstatusDao extends AbstractBaseT {  

	@Test
	public void Test1() throws Exception {     
		OrderstatusDao dao = getDaoFactory().getOrderstatusDao();
		List<Orderstatus> list = dao.getAll(1,10);
		for (Orderstatus bean : list)
			System.out.println(bean);
	}

}
