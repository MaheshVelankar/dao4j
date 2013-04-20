package org.example.petstore.persistence.dto;

import it.mengoni.persistence.dto.PersistentObject;

import java.util.List;


public interface Supplier extends PersistentObject {
    public Integer getSuppid();

    public void setSuppid(Integer suppid);

    public String getName();

    public void setName(String name);

    public String getStatus();

    public void setStatus(String status);

    public String getAddr1();

    public void setAddr1(String addr1);

    public String getAddr2();

    public void setAddr2(String addr2);

    public String getCity();

    public void setCity(String city);

    public String getState();

    public void setState(String state);

    public String getZip();

    public void setZip(String zip);

    public String getPhone();

    public void setPhone(String phone);

    public List<Item> getListItem();

    public void setListItem(List<Item> toItem);
}
