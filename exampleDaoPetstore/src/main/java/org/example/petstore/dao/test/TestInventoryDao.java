package org.example.petstore.dao.test;
import org.example.petstore.dao.dto.Inventory;
import java.util.List;
import org.example.petstore.dao.dao.InventoryDao;
import org.junit.Test;
public class TestInventoryDao extends AbstractBaseT {  

	@Test
	public void Test1() throws Exception {     
		InventoryDao dao = getDaoFactory().getInventoryDao();
		List<Inventory> list = dao.getAll(1,10);
		for (Inventory bean : list)
			System.out.println(bean);
	}

}
