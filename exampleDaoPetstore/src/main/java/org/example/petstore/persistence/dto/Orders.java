package org.example.petstore.persistence.dto;

import it.mengoni.persistence.dto.PersistentObject;

import java.math.BigDecimal;

import java.sql.Date;


public interface Orders extends PersistentObject {
    public Integer getOrderid();

    public void setOrderid(Integer orderid);

    public String getUserid();

    public void setUserid(String userid);

    public Date getOrderdate();

    public void setOrderdate(Date orderdate);

    public String getShipaddr1();

    public void setShipaddr1(String shipaddr1);

    public String getShipaddr2();

    public void setShipaddr2(String shipaddr2);

    public String getShipcity();

    public void setShipcity(String shipcity);

    public String getShipstate();

    public void setShipstate(String shipstate);

    public String getShipzip();

    public void setShipzip(String shipzip);

    public String getShipcountry();

    public void setShipcountry(String shipcountry);

    public String getBilladdr1();

    public void setBilladdr1(String billaddr1);

    public String getBilladdr2();

    public void setBilladdr2(String billaddr2);

    public String getBillcity();

    public void setBillcity(String billcity);

    public String getBillstate();

    public void setBillstate(String billstate);

    public String getBillzip();

    public void setBillzip(String billzip);

    public String getBillcountry();

    public void setBillcountry(String billcountry);

    public String getCourier();

    public void setCourier(String courier);

    public BigDecimal getTotalprice();

    public void setTotalprice(BigDecimal totalprice);

    public String getBilltofirstname();

    public void setBilltofirstname(String billtofirstname);

    public String getBilltolastname();

    public void setBilltolastname(String billtolastname);

    public String getShiptofirstname();

    public void setShiptofirstname(String shiptofirstname);

    public String getShiptolastname();

    public void setShiptolastname(String shiptolastname);

    public String getCreditcard();

    public void setCreditcard(String creditcard);

    public String getExprdate();

    public void setExprdate(String exprdate);

    public String getCardtype();

    public void setCardtype(String cardtype);

    public String getLocale();

    public void setLocale(String locale);
}
