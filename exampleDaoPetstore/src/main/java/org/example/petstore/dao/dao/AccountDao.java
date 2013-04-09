package org.example.petstore.dao.dao;
import org.example.petstore.dao.dto.Account;
import it.mengoni.persistence.dao.Dao;
public interface AccountDao extends Dao<Account> {
public Account getByPrimaryKey(String userid);
}
