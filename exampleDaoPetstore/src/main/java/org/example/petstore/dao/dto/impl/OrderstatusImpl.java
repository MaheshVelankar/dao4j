package org.example.petstore.dao.dto.impl;
import java.sql.Date;
import org.javatuples.Tuple;
import org.example.petstore.dao.dto.Orderstatus;
import org.javatuples.Pair;
import it.mengoni.persistence.dto.AbstractPersistentObject;
public class OrderstatusImpl extends AbstractPersistentObject implements Orderstatus  {
	private static final long serialVersionUID = 1L;
private Integer orderid ;
private Integer linenum ;
private Date timestamp ;
private String status ;
    public OrderstatusImpl(){
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
public Integer getLinenum() {
return linenum;
}
@Override
public void setLinenum(Integer linenum) {
this.linenum = linenum;
}
@Override
public Date getTimestamp() {
return timestamp;
}
@Override
public void setTimestamp(Date timestamp) {
this.timestamp = timestamp;
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
    public String getDisplayLabel() {
         return status;
    }
   @Override
    protected Tuple newKey() {
    return new Pair<Integer, Integer>(orderid, linenum);
}
}
