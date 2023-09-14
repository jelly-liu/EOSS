package com.open.eoss.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class ComUtil {
	private static Logger logger = LoggerFactory.getLogger(ComUtil.class);

	public static final String NewUUID(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	public static String parseJSessionId(HttpServletRequest request){
		String cookie = request.getHeader("Cookie");
		if(StringUtils.isBlank(cookie)){
			return null;
		}

		String JSESSIONID = StringUtils.substringAfter(cookie, "JSESSIONID=");
		JSESSIONID = StringUtils.substringBefore(JSESSIONID, ";");
		return JSESSIONID;
	}
}
