package org.example.petstore.persistence.dto;

import it.mengoni.persistence.dto.PersistentObject;

import java.sql.Date;


public interface Orderstatus extends PersistentObject {
    public Integer getOrderid();

    public void setOrderid(Integer orderid);

    public Integer getLinenum();

    public void setLinenum(Integer linenum);

    public Date getTimestamp();

    public void setTimestamp(Date timestamp);

    public String getStatus();

    public void setStatus(String status);
}
