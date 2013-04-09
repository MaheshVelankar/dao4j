package org.example.petstore.dao.dto;
import java.math.BigDecimal;
import it.mengoni.persistence.dto.PersistentObject;
public interface Lineitem extends PersistentObject {
public Integer getOrderid();
public void setOrderid(Integer orderid);
public Integer getLinenum();
public void setLinenum(Integer linenum);
public String getItemid();
public void setItemid(String itemid);
public Integer getQuantity();
public void setQuantity(Integer quantity);
public BigDecimal getUnitprice();
public void setUnitprice(BigDecimal unitprice);
}
