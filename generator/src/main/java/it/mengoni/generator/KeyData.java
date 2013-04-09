package it.mengoni.generator;

import it.mengoni.jdbc.model.TableColunm;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.javatuples.Decade;
import org.javatuples.Ennead;
import org.javatuples.Octet;
import org.javatuples.Pair;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Septet;
import org.javatuples.Sextet;
import org.javatuples.Triplet;
import org.javatuples.Unit;

public class KeyData implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	Class<?> tupleClass = null;
	Set<String> importSet = new HashSet<String>();
	StringBuilder ka = new StringBuilder();
	StringBuilder kf = new StringBuilder();
	StringBuilder kp = new StringBuilder();
	public String tupleType;

	public KeyData(List<TableColunm> children) {
		int cp = 0;
		ka.append("<");
		for (int i = 0; i < children.size(); i++) {
			TableColunm col = children.get(i);
			if (col.isPk()) {
				if (cp > 0) {
					ka.append(", ");
					kf.append(", ");
					kp.append(", ");
				}
				ka.append(Helper.getJavaType(col.getSqlType(), importSet));
				String np = Helper.toCamel(col.getJavaName(), false);
				kf.append(np);
				kp.append(Helper.getJavaType(col.getSqlType(), importSet)).append(" ").append(np);
				cp++;
			}
		}
		ka.append(">");
		switch (cp) {
		case 1:
			tupleClass = Unit.class;
			break;
		case 2:
			tupleClass = Pair.class;
			break;
		case 3:
			tupleClass = Triplet.class;
			break;
		case 4:
			tupleClass = Quartet.class;
			break;
		case 5:
			tupleClass = Quintet.class;
			break;
		case 6:
			tupleClass = Sextet.class;
			break;
		case 7:
			tupleClass = Septet.class;
			break;
		case 8:
			tupleClass = Octet.class;
			break;
		case 9:
			tupleClass = Ennead.class;
			break;
		case 10:
			tupleClass = Decade.class;
			break;
		default:
			tupleClass = Unit.class;
			break;
		}
		if (tupleClass != null)
			tupleType = tupleClass.getSimpleName();
		else
			tupleType = "Unit";
	}

}