package com.jelly.eoss.util;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.servlet.ServletContext;

public class Const {
	public static String BASE_PATH;
	public static String PROJECT_NAME;
	public static String PROJECT_VERSION;
	public static String COPY_RIGHT;
	public static int PAGE_SIZE;
	public static String LOGIN_SESSION_KEY = "EossLoginUser";
	public static String LOGIN_MENU_TREE_IDS_KEY = "EossLoginMenuTreeId";
	public static String REAL_BASE_PATH;
	public static boolean ENABLE_SECURITY_FILTER;
	
	public static JdbcTemplate jdbcTemplate;
	public static SqlSessionTemplate sqlSessionTemplate;
	public static ServletContext servletContext;
}
