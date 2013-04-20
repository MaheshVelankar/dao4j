package org.example.petstore.persistence.dto.impl;

import it.mengoni.persistence.dto.AbstractPersistentObject;

import org.example.petstore.persistence.dto.Signon;

import org.javatuples.Tuple;
import org.javatuples.Unit;


public class SignonImpl extends AbstractPersistentObject implements Signon {
    private static final long serialVersionUID = 1L;
    private String username;
    private String password;

    public SignonImpl() {
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getDisplayLabel() {
        return username;
    }

    @Override
    protected Tuple newKey() {
        return new Unit<String>(username);
    }
}
