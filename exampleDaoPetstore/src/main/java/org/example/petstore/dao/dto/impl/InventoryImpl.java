package org.example.petstore.dao.dto.impl;
import org.example.petstore.dao.dto.Inventory;
import org.javatuples.Tuple;
import it.mengoni.persistence.dto.AbstractPersistentObject;
import org.javatuples.Unit;
public class InventoryImpl extends AbstractPersistentObject implements Inventory  {
	private static final long serialVersionUID = 1L;
private String itemid ;
private Integer qty ;
    public InventoryImpl(){
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
public Integer getQty() {
return qty;
}
@Override
public void setQty(Integer qty) {
this.qty = qty;
}
    @Override
    public String getDisplayLabel() {
         return itemid;
    }
   @Override
    protected Tuple newKey() {
    return new Unit<String>(itemid);
}
}
