package it.mengoni.persistence.dao;

import it.mengoni.exception.LogicError;
import it.mengoni.exception.SystemError;

import java.util.List;
import java.util.Map;

public class PostgresqlSequenceReader {

	private final static String format = "SELECT nextval('%s')";
	private final String generatorName;

	public PostgresqlSequenceReader(String generatorName) {
		super();
		this.generatorName = generatorName;
	}

	public Long readSequence(JdbcHelper jdbcHelper) throws SystemError, LogicError {
		List<Map<String, Object>> rs = jdbcHelper.queryFor(String.format(format, generatorName));
		return  (Long) rs.get(0).get("nextval");
	}

}
