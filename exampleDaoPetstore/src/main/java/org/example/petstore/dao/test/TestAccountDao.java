package org.example.petstore.dao.test;
import org.example.petstore.dao.dto.Account;
import java.util.List;
import org.example.petstore.dao.dao.AccountDao;
import org.junit.Test;
public class TestAccountDao extends AbstractBaseT {  

	@Test
	public void Test1() throws Exception {     
		AccountDao dao = getDaoFactory().getAccountDao();
		List<Account> list = dao.getAll(1,10);
		for (Account bean : list)
			System.out.println(bean);
	}

}
