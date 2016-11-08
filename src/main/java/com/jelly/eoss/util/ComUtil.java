package com.jelly.eoss.util;

import java.util.Map;
import java.util.UUID;

public class ComUtil {
	public static final String NewUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	public static final synchronized int QueryNextID(String columnName, String tableName){
		String sqlTemplate = "select max(columnName) as id from tableName";
		sqlTemplate = sqlTemplate.replaceAll("columnName", columnName);
		sqlTemplate = sqlTemplate.replaceAll("tableName", tableName);
		
		Map<String, Object> m = Const.jdbcTemplate.queryForMap(sqlTemplate);
//		System.out.println("m:" + m);
		if(m == null || m.get("id") == null){
//			System.out.println("mId:" + m.get("id"));
			return 1;
		}
		
		String idStr = m.get("id").toString();
//		Log.Info(idStr);
		if(idStr == null || idStr.equalsIgnoreCase("null") || idStr.equals("")){
			return 1;
		}
		
		return Integer.parseInt(idStr) + 1;
	}
	
}
