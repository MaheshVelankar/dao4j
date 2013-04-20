package org.example.petstore.persistence.dto.impl;

import it.mengoni.persistence.dto.AbstractPersistentObject;

import org.example.petstore.persistence.dto.Profile;

import org.javatuples.Tuple;
import org.javatuples.Unit;


public class ProfileImpl extends AbstractPersistentObject implements Profile {
    private static final long serialVersionUID = 1L;
    private String userid;
    private String langpref;
    private String favcategory;
    private Boolean mylistopt;
    private Boolean banneropt;

    public ProfileImpl() {
    }

    @Override
    public String getUserid() {
        return userid;
    }

    @Override
    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public String getLangpref() {
        return langpref;
    }

    @Override
    public void setLangpref(String langpref) {
        this.langpref = langpref;
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
    public Boolean getMylistopt() {
        return mylistopt;
    }

    @Override
    public void setMylistopt(Boolean mylistopt) {
        this.mylistopt = mylistopt;
    }

    @Override
    public Boolean getBanneropt() {
        return banneropt;
    }

    @Override
    public void setBanneropt(Boolean banneropt) {
        this.banneropt = banneropt;
    }

    @Override
    public String getDisplayLabel() {
        return userid;
    }

    @Override
    protected Tuple newKey() {
        return new Unit<String>(userid);
    }
}
