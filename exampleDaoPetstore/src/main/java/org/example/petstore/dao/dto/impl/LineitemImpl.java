package org.example.petstore.dao.dto.impl;
import org.example.petstore.dao.dto.Lineitem;
import org.javatuples.Tuple;
import org.javatuples.Pair;
import it.mengoni.persistence.dto.AbstractPersistentObject;
import java.math.BigDecimal;
public class LineitemImpl extends AbstractPersistentObject implements Lineitem  {
	private static final long serialVersionUID = 1L;
private Integer orderid ;
private Integer linenum ;
private String itemid ;
private Integer quantity ;
private BigDecimal unitprice ;
    public LineitemImpl(){
}
@Override
public Integer getOrderid() {
return orderid;
}
@Override
public void setOrderid(Integer orderid) {
this.orderid = orderid;
}
@Override
public Integer getLinenum() {
return linenum;
}
@Override
public void setLinenum(Integer linenum) {
this.linenum = linenum;
}
@Override
public String getItemid() {
return itemid;
}
@Override
public void setItemid(String itemid) {
this.itemid = itemid;
}
@Override
public Integer getQuantity() {
return quantity;
}
@Override
public void setQuantity(Integer quantity) {
this.quantity = quantity;
}
@Override
public BigDecimal getUnitprice() {
return unitprice;
}
@Override
public void setUnitprice(BigDecimal unitprice) {
this.unitprice = unitprice;
}
    @Override
    public String getDisplayLabel() {
         return itemid;
    }
   @Override
    protected Tuple newKey() {
    return new Pair<Integer, Integer>(orderid, linenum);
}
}
