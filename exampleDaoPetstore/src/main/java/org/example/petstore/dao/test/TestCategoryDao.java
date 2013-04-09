package org.example.petstore.dao.test;
import org.example.petstore.dao.dto.Category;
import java.util.List;
import org.example.petstore.dao.dao.CategoryDao;
import org.junit.Test;
public class TestCategoryDao extends AbstractBaseT {  

	@Test
	public void Test1() throws Exception {     
		CategoryDao dao = getDaoFactory().getCategoryDao();
		List<Category> list = dao.getAll(1,10);
		for (Category bean : list)
			System.out.println(bean);
	}

}
