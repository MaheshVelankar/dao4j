package org.example.petstore.dao.dto.impl;
import it.mengoni.persistence.dto.PoLazyProperty;
import org.example.petstore.dao.dto.Category;
import java.util.List;
import it.mengoni.persistence.dto.AbstractPersistentObject;
import org.example.petstore.dao.DaoFactory;
import org.javatuples.Unit;
import it.mengoni.persistence.dto.PoProperties;
import org.example.petstore.dao.dto.Item;
import org.javatuples.Tuple;
import org.example.petstore.dao.dto.Product;
import it.mengoni.persistence.dto.PoProperty;
import it.mengoni.persistence.dto.PoLazyProperties;
import it.mengoni.persistence.dao.ConditionValue;
public class ProductImpl extends AbstractPersistentObject implements Product  {
	private static final long serialVersionUID = 1L;
private String productid ;
private String category ;
private String name ;
private String descn ;
    public ProductImpl(){
}
@Override
public String getProductid() {
return productid;
}
@Override
public void setProductid(String productid) {
this.productid = productid;
}
@Override
public String getCategory() {
return category;
}
@Override
public void setCategory(String category) {
this.category = category;
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
         return productid;
    }
private transient PoProperty<Category> toCategory= new PoLazyProperty<Category>();
public Category getToCategory(){ 
return toCategory.getValue(DaoFactory.getInstance().getCategoryDao(), category);
}
public void setToCategory(Category toCategory){
    this.toCategory.setValue(toCategory);
}
private transient PoProperties<Item> listItem= new PoLazyProperties<Item>();
public List<Item> getListItem(){ 
return listItem.getValue(DaoFactory.getInstance().getItemDao(), new ConditionValue("productid = ?", productid));
}
public void setListItem(List<Item> listItem){
    this.listItem.setValue(listItem);
}
   @Override
    protected Tuple newKey() {
    return new Unit<String>(productid);
}
}
