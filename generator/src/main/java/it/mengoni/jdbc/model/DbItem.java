package it.mengoni.jdbc.model;

import java.io.Serializable;
import java.util.Iterator;

public interface DbItem extends Serializable {

	public static final String DEFAULT_ITEM = "default";

	public String getDescrizione();

	public void setDescrizione(String descrizione);

	public String getDbName();

	public void setDbName(String name);

	public String getJavaName();

	public boolean isDefault();

	public String getLabel();

//	public DbItem getParent();

	public boolean haveChildren();

	public int getChildCount();

	public Iterator<DbItem> getChildIterator();

}