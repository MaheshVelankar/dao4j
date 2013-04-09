package org.example.petstore.dao.dto.impl;
import org.javatuples.Tuple;
import org.example.petstore.dao.dto.Account;
import it.mengoni.persistence.dto.AbstractPersistentObject;
import org.javatuples.Unit;
public class AccountImpl extends AbstractPersistentObject implements Account  {
	private static final long serialVersionUID = 1L;
private String userid ;
private String email ;
private String firstname ;
private String lastname ;
private String status ;
private String addr1 ;
private String addr2 ;
private String city ;
private String state ;
private String zip ;
private String country ;
private String phone ;
    public AccountImpl(){
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
public String getEmail() {
return email;
}
@Override
public void setEmail(String email) {
this.email = email;
}
@Override
public String getFirstname() {
return firstname;
}
@Override
public void setFirstname(String firstname) {
this.firstname = firstname;
}
@Override
public String getLastname() {
return lastname;
}
@Override
public void setLastname(String lastname) {
this.lastname = lastname;
}
@Override
public String getStatus() {
return status;
}
@Override
public void setStatus(String status) {
this.status = status;
}
@Override
public String getAddr1() {
return addr1;
}
@Override
public void setAddr1(String addr1) {
this.addr1 = addr1;
}
@Override
public String getAddr2() {
return addr2;
}
@Override
public void setAddr2(String addr2) {
this.addr2 = addr2;
}
@Override
public String getCity() {
return city;
}
@Override
public void setCity(String city) {
this.city = city;
}
@Override
public String getState() {
return state;
}
@Override
public void setState(String state) {
this.state = state;
}
@Override
public String getZip() {
return zip;
}
@Override
public void setZip(String zip) {
this.zip = zip;
}
@Override
public String getCountry() {
return country;
}
@Override
public void setCountry(String country) {
this.country = country;
}
@Override
public String getPhone() {
return phone;
}
@Override
public void setPhone(String phone) {
this.phone = phone;
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
