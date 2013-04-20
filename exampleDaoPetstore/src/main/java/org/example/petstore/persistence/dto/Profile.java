package org.example.petstore.persistence.dto;

import it.mengoni.persistence.dto.PersistentObject;


public interface Profile extends PersistentObject {
    public String getUserid();

    public void setUserid(String userid);

    public String getLangpref();

    public void setLangpref(String langpref);

    public String getFavcategory();

    public void setFavcategory(String favcategory);

    public Boolean getMylistopt();

    public void setMylistopt(Boolean mylistopt);

    public Boolean getBanneropt();

    public void setBanneropt(Boolean banneropt);
}
