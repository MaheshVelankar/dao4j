package org.example.petstore.dao.test;
import org.example.petstore.dao.dao.OrdersDao;
import org.example.petstore.dao.dto.Orders;
import java.util.List;
import org.junit.Test;
public class TestOrdersDao extends AbstractBaseT {  

	@Test
	public void Test1() throws Exception {     
		OrdersDao dao = getDaoFactory().getOrdersDao();
		List<Orders> list = dao.getAll(1,10);
		for (Orders bean : list)
			System.out.println(bean);
	}

}
