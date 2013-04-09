package org.example.petstore.dao.dto.impl;
import java.sql.Date;
import org.javatuples.Tuple;
import org.example.petstore.dao.dto.Orders;
import it.mengoni.persistence.dto.AbstractPersistentObject;
import java.math.BigDecimal;
import org.javatuples.Unit;
public class OrdersImpl extends AbstractPersistentObject implements Orders  {
	private static final long serialVersionUID = 1L;
private Integer orderid ;
private String userid ;
private Date orderdate ;
private String shipaddr1 ;
private String shipaddr2 ;
private String shipcity ;
private String shipstate ;
private String shipzip ;
private String shipcountry ;
private String billaddr1 ;
private String billaddr2 ;
private String billcity ;
private String billstate ;
private String billzip ;
private String billcountry ;
private String courier ;
private BigDecimal totalprice ;
private String billtofirstname ;
private String billtolastname ;
private String shiptofirstname ;
private String shiptolastname ;
private String creditcard ;
private String exprdate ;
private String cardtype ;
private String locale ;
    public OrdersImpl(){
}
@Override
public Integer getOrderid() {
return orderid;
}
@Override
public void setOrderid(Integer orderid) {
this.orderid = orderid;
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
public Date getOrderdate() {
return orderdate;
}
@Override
public void setOrderdate(Date orderdate) {
this.orderdate = orderdate;
}
@Override
public String getShipaddr1() {
return shipaddr1;
}
@Override
public void setShipaddr1(String shipaddr1) {
this.shipaddr1 = shipaddr1;
}
@Override
public String getShipaddr2() {
return shipaddr2;
}
@Override
public void setShipaddr2(String shipaddr2) {
this.shipaddr2 = shipaddr2;
}
@Override
public String getShipcity() {
return shipcity;
}
@Override
public void setShipcity(String shipcity) {
this.shipcity = shipcity;
}
@Override
public String getShipstate() {
return shipstate;
}
@Override
public void setShipstate(String shipstate) {
this.shipstate = shipstate;
}
@Override
public String getShipzip() {
return shipzip;
}
@Override
public void setShipzip(String shipzip) {
this.shipzip = shipzip;
}
@Override
public String getShipcountry() {
return shipcountry;
}
@Override
public void setShipcountry(String shipcountry) {
this.shipcountry = shipcountry;
}
@Override
public String getBilladdr1() {
return billaddr1;
}
@Override
public void setBilladdr1(String billaddr1) {
this.billaddr1 = billaddr1;
}
@Override
public String getBilladdr2() {
return billaddr2;
}
@Override
public void setBilladdr2(String billaddr2) {
this.billaddr2 = billaddr2;
}
@Override
public String getBillcity() {
return billcity;
}
@Override
public void setBillcity(String billcity) {
this.billcity = billcity;
}
@Override
public String getBillstate() {
return billstate;
}
@Override
public void setBillstate(String billstate) {
this.billstate = billstate;
}
@Override
public String getBillzip() {
return billzip;
}
@Override
public void setBillzip(String billzip) {
this.billzip = billzip;
}
@Override
public String getBillcountry() {
return billcountry;
}
@Override
public void setBillcountry(String billcountry) {
this.billcountry = billcountry;
}
@Override
public String getCourier() {
return courier;
}
@Override
public void setCourier(String courier) {
this.courier = courier;
}
@Override
public BigDecimal getTotalprice() {
return totalprice;
}
@Override
public void setTotalprice(BigDecimal totalprice) {
this.totalprice = totalprice;
}
@Override
public String getBilltofirstname() {
return billtofirstname;
}
@Override
public void setBilltofirstname(String billtofirstname) {
this.billtofirstname = billtofirstname;
}
@Override
public String getBilltolastname() {
return billtolastname;
}
@Override
public void setBilltolastname(String billtolastname) {
this.billtolastname = billtolastname;
}
@Override
public String getShiptofirstname() {
return shiptofirstname;
}
@Override
public void setShiptofirstname(String shiptofirstname) {
this.shiptofirstname = shiptofirstname;
}
@Override
public String getShiptolastname() {
return shiptolastname;
}
@Override
public void setShiptolastname(String shiptolastname) {
this.shiptolastname = shiptolastname;
}
@Override
public String getCreditcard() {
return creditcard;
}
@Override
public void setCreditcard(String creditcard) {
this.creditcard = creditcard;
}
@Override
public String getExprdate() {
return exprdate;
}
@Override
public void setExprdate(String exprdate) {
this.exprdate = exprdate;
}
@Override
public String getCardtype() {
return cardtype;
}
@Override
public void setCardtype(String cardtype) {
this.cardtype = cardtype;
}
@Override
public String getLocale() {
return locale;
}
@Override
public void setLocale(String locale) {
this.locale = locale;
}
    @Override
    public String getDisplayLabel() {
         return userid;
    }
   @Override
    protected Tuple newKey() {
    return new Unit<Integer>(orderid);
}
}
