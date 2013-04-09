package org.example.petstore.dao.test;

import java.util.List;

import org.example.petstore.dao.dao.AccountDao;
import org.example.petstore.dao.dto.Account;
import org.junit.Test;

public class TestDao01 extends AbstractTest {

	@Test
	public void test01() {
		AccountDao dao = getDf().getAccountDao();
		List<Account> list = dao.getAll();
		for (Account account : list) {
			System.out.println(account);
		}

	}

}
