package org.example.petstore.dao.dto;
import it.mengoni.persistence.dto.PersistentObject;
public interface Sequence extends PersistentObject {
public String getName();
public void setName(String name);
public Integer getNextid();
public void setNextid(Integer nextid);
}
