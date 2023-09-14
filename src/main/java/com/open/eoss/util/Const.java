package com.open.eoss.util;

import org.mybatis.spring.SqlSessionTemplate;

public class Const {
	public static String BASE_PATH;
	public static String PROJECT_NAME;
	public static String PROJECT_VERSION;
	public static String COPY_RIGHT;
	public static int PAGE_SIZE = 10;
	public static String LOGIN_SESSION_KEY = "EossLoginUser";
	public static String LOGIN_MENU_TREE_IDS_KEY = "EossLoginMenuTreeId";
	public static String REAL_BASE_PATH;

	public static SqlSessionTemplate sqlSessionTemplate;

	public static String OFFSET = "offset";
	public static String LENGTH = "length";

	public static Integer RobotUserId = 1;
}
