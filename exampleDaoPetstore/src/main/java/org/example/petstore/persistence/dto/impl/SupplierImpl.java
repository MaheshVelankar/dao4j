package org.example.petstore.persistence.dto.impl;

import it.mengoni.persistence.dao.ConditionValue;
import it.mengoni.persistence.dto.AbstractPersistentObject;
import it.mengoni.persistence.dto.PoLazyProperties;
import it.mengoni.persistence.dto.PoProperties;

import org.example.petstore.persistence.DaoFactory;
import org.example.petstore.persistence.dto.Item;
import org.example.petstore.persistence.dto.Supplier;

import org.javatuples.Tuple;
import org.javatuples.Unit;

import java.util.List;


public class SupplierImpl extends AbstractPersistentObject implements Supplier {
    private static final long serialVersionUID = 1L;
    private Integer suppid;
    private String name;
    private String status;
    private String addr1;
    private String addr2;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private transient PoProperties<Item> listItem = new PoLazyProperties<Item>();

    public SupplierImpl() {
    }

    @Override
    public Integer getSuppid() {
        return suppid;
    }

    @Override
    public void setSuppid(Integer suppid) {
        this.suppid = suppid;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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
    public String getPhone() {
        return phone;
    }

    @Override
    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String getDisplayLabel() {
        return name;
    }

    public List<Item> getListItem() {
        if (suppid == null) {
            return null;
        }

        return listItem.getValue(DaoFactory.getInstance().getItemDao(),
            new ConditionValue("supplier = ?", suppid));
    }

    public void setListItem(List<Item> listItem) {
        this.listItem.setValue(listItem);
    }

    @Override
    protected Tuple newKey() {
        return new Unit<Integer>(suppid);
    }
}
