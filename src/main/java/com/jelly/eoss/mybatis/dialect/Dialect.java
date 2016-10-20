package com.jelly.eoss.mybatis.dialect;


public abstract class Dialect {
	public abstract String getPageSql(String sql, int start, int limit);
}
