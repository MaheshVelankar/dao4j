package org.example.petstore.persistence.dto;

import it.mengoni.persistence.dto.PersistentObject;


public interface Bannerdata extends PersistentObject {
    public String getFavcategory();

    public void setFavcategory(String favcategory);

    public String getBannername();

    public void setBannername(String bannername);
}
