package org.example.petstore.dao.test;
import org.example.petstore.dao.dao.SupplierDao;
import java.util.List;
import org.example.petstore.dao.dto.Supplier;
import org.junit.Test;
public class TestSupplierDao extends AbstractBaseT {  

	@Test
	public void Test1() throws Exception {     
		SupplierDao dao = getDaoFactory().getSupplierDao();
		List<Supplier> list = dao.getAll(1,10);
		for (Supplier bean : list)
			System.out.println(bean);
	}

}
