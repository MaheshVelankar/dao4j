package org.example.petstore.dao.dto.impl;
import org.javatuples.Tuple;
import org.example.petstore.dao.dto.Bannerdata;
import it.mengoni.persistence.dto.AbstractPersistentObject;
import org.javatuples.Unit;
public class BannerdataImpl extends AbstractPersistentObject implements Bannerdata  {
	private static final long serialVersionUID = 1L;
private String favcategory ;
private String bannername ;
    public BannerdataImpl(){
}
@Override
public String getFavcategory() {
return favcategory;
}
@Override
public void setFavcategory(String favcategory) {
this.favcategory = favcategory;
}
@Override
public String getBannername() {
return bannername;
}
@Override
public void setBannername(String bannername) {
this.bannername = bannername;
}
    @Override
    public String getDisplayLabel() {
         return favcategory;
    }
   @Override
    protected Tuple newKey() {
    return new Unit<String>(favcategory);
}
}
