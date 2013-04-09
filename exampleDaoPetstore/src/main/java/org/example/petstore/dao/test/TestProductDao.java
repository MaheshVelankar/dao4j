package org.example.petstore.dao.test;
import org.example.petstore.dao.dto.Product;
import java.util.List;
import org.example.petstore.dao.dao.ProductDao;
import org.junit.Test;
public class TestProductDao extends AbstractBaseT {  

	@Test
	public void Test1() throws Exception {     
		ProductDao dao = getDaoFactory().getProductDao();
		List<Product> list = dao.getAll(1,10);
		for (Product bean : list)
			System.out.println(bean);
	}

}
