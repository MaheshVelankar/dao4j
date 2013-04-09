package org.example.petstore.dao.dto;
import java.util.List;
import it.mengoni.persistence.dto.PersistentObject;
public interface Category extends PersistentObject {
public String getCatid();
public void setCatid(String catid);
public String getName();
public void setName(String name);
public String getDescn();
public void setDescn(String descn);
public List<Product> getListProduct();
public void setListProduct(List<Product> toProduct);
}
