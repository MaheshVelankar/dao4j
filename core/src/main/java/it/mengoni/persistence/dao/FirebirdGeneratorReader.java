package it.mengoni.persistence.dao;

import it.mengoni.persistence.exception.LogicError;
import it.mengoni.persistence.exception.SystemError;

import java.util.List;
import java.util.Map;

public class FirebirdGeneratorReader {

	private final static String format = "SELECT GEN_ID(%s, 1) AS VALORE FROM RDB$DATABASE";
	private final String generatorName;

	public FirebirdGeneratorReader(String generatorName) {
		super();
		this.generatorName = generatorName;
	}

	public Long readGenerator(JdbcHelper jdbcHelper) throws SystemError, LogicError {
		List<Map<String, Object>> rs = jdbcHelper.queryFor(String.format(format, generatorName));
		return  (Long) rs.get(0).get("VALORE");
	}

}
