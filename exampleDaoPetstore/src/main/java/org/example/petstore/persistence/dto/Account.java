package org.example.petstore.persistence.dto;

import it.mengoni.persistence.dto.PersistentObject;


public interface Account extends PersistentObject {
    public String getUserid();

    public void setUserid(String userid);

    public String getEmail();

    public void setEmail(String email);

    public String getFirstname();

    public void setFirstname(String firstname);

    public String getLastname();

    public void setLastname(String lastname);

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

    public String getCountry();

    public void setCountry(String country);

    public String getPhone();

    public void setPhone(String phone);
}
