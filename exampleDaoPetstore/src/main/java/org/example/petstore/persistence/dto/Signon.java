package org.example.petstore.persistence.dto;

import it.mengoni.persistence.dto.PersistentObject;


public interface Signon extends PersistentObject {
    public String getUsername();

    public void setUsername(String username);

    public String getPassword();

    public void setPassword(String password);
}
