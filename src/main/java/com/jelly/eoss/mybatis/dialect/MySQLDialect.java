package com.jelly.eoss.mybatis.dialect;


public class MySQLDialect extends Dialect {
	
	@Override
	public String getPageSql(String sql, int start, int limit) {
		sql = sql + " limit " + start + ", " + limit;
		return sql;
	}

}
