package com.jelly.eoss.mybatis.dialect;


public class OracleDialect extends Dialect {
	
	@Override
	public String getPageSql(String sql, int start, int limit) {
		StringBuilder sqlSb = new StringBuilder();
		sqlSb.append("SELECT * FROM (SELECT a1000.*, ROWNUM rn FROM (");
		sqlSb.append(sql);
		sqlSb.append(")a1000 WHERE ROWNUM <= ").append(start + limit);
		sqlSb.append(") WHERE rn >= ").append(start + 1);
		return sqlSb.toString();
	}

}
