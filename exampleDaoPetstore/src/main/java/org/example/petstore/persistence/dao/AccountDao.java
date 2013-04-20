package org.example.petstore.persistence.dao;

import it.mengoni.persistence.dao.Dao;

import org.example.petstore.persistence.dto.Account;


public interface AccountDao extends Dao<Account> {
    public Account getByPrimaryKey(String userid);
}
