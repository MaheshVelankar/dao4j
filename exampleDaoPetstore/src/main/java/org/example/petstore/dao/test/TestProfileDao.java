package org.example.petstore.dao.test;
import org.example.petstore.dao.dao.ProfileDao;
import java.util.List;
import org.example.petstore.dao.dto.Profile;
import org.junit.Test;
public class TestProfileDao extends AbstractBaseT {  

	@Test
	public void Test1() throws Exception {     
		ProfileDao dao = getDaoFactory().getProfileDao();
		List<Profile> list = dao.getAll(1,10);
		for (Profile bean : list)
			System.out.println(bean);
	}

}
