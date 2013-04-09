package org.example.petstore.dao.test;
import org.example.petstore.dao.dao.BannerdataDao;
import org.example.petstore.dao.dto.Bannerdata;
import java.util.List;
import org.junit.Test;
public class TestBannerdataDao extends AbstractBaseT {  

	@Test
	public void Test1() throws Exception {     
		BannerdataDao dao = getDaoFactory().getBannerdataDao();
		List<Bannerdata> list = dao.getAll(1,10);
		for (Bannerdata bean : list)
			System.out.println(bean);
	}

}
