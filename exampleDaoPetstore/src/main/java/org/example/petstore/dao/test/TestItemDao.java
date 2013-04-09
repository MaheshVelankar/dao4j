package org.example.petstore.dao.test;
import org.example.petstore.dao.dto.Item;
import java.util.List;
import org.example.petstore.dao.dao.ItemDao;
import org.junit.Test;
public class TestItemDao extends AbstractBaseT {  

	@Test
	public void Test1() throws Exception {     
		ItemDao dao = getDaoFactory().getItemDao();
		List<Item> list = dao.getAll(1,10);
		for (Item bean : list)
			System.out.println(bean);
	}

}
