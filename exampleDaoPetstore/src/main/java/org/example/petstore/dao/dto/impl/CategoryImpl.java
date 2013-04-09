package org.example.petstore.dao.dto.impl;
import org.example.petstore.dao.dto.Product;
import org.javatuples.Tuple;
import org.example.petstore.dao.dto.Category;
import java.util.List;
import it.mengoni.persistence.dto.AbstractPersistentObject;
import org.example.petstore.dao.DaoFactory;
import org.javatuples.Unit;
import it.mengoni.persistence.dto.PoProperties;
import it.mengoni.persistence.dto.PoLazyProperties;
import it.mengoni.persistence.dao.ConditionValue;
public class CategoryImpl extends AbstractPersistentObject implements Category  {
	private static final long serialVersionUID = 1L;
private String catid ;
private String name ;
private String descn ;
    public CategoryImpl(){
}
@Override
public String getCatid() {
return catid;
}
@Override
public void setCatid(String catid) {
this.catid = catid;
}
@Override
public String getName() {
return name;
}
@Override
public void setName(String name) {
this.name = name;
}
@Override
public String getDescn() {
return descn;
}
@Override
public void setDescn(String descn) {
this.descn = descn;
}
    @Override
    public String getDisplayLabel() {
         return catid;
    }
private transient PoProperties<Product> listProduct= new PoLazyProperties<Product>();
public List<Product> getListProduct(){ 
return listProduct.getValue(DaoFactory.getInstance().getProductDao(), new ConditionValue("category = ?", catid));
}
public void setListProduct(List<Product> listProduct){
    this.listProduct.setValue(listProduct);
}
   @Override
    protected Tuple newKey() {
    return new Unit<String>(catid);
}
}
