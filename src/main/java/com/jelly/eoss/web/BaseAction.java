package com.jelly.eoss.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class BaseAction {
	private static final Logger log = LoggerFactory.getLogger(BaseAction.class);

	protected boolean isEmptyString(String string){
		boolean flag = false;
		if(string == null || string.trim().equals("")){
			flag = true;
		}
		return flag;
	}
	
	//{"flag":"true","msg":"ErrorMessage"}
	protected void responseSimpleJson(HttpServletResponse response, boolean flag, String msg) throws IOException{
		response.setContentType("application/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		out.write("{\"flag\":" + flag + ",\"msg\":\"" + msg + "\"}");
		out.flush();
	}
	
	//重新设置request域的所有参数
    protected void resetAllRequestParams(HttpServletRequest request){
    	Enumeration<String> paramNamesEnumeration = request.getParameterNames();
        while (paramNamesEnumeration.hasMoreElements()) {
        	String key = paramNamesEnumeration.nextElement();
        	request.setAttribute(key, request.getParameter(key));
        }
    }
    
    //封装request域所有参数为Map，checkbox这样的值将会被拼接成一个"分号"分隔的字符串
    protected Map<String, String> getRequestMap(HttpServletRequest request){
    	Map<String, String> paramMap = new HashMap<String, String>();
    	Enumeration<String> paramNamesEnumeration = request.getParameterNames();
    	String key = null;
    	String[] values = null;
    	StringBuffer sb = new StringBuffer();
        while (paramNamesEnumeration.hasMoreElements()) {
        	sb.delete(0, sb.length());
        	key = paramNamesEnumeration.nextElement();
        	values = request.getParameterValues(key);
        	for(String v : values){
        		sb.append(v + ";");
        	}
        	if(sb.length() > 0){
        		sb.deleteCharAt(sb.length() - 1);
        	}
        	log.debug("requestParams:" + key + "---->" + sb.toString());
        	paramMap.put(key, sb.toString());
        }
    	return paramMap;
    }
    
    //封装request域所有参数为key1=value1&key2=value2的字符串
    //includePage：是否包括名为page的参数
    protected String getRequestParamsString(HttpServletRequest request, boolean includePage){
    	StringBuffer sb = new StringBuffer();
    	Enumeration<String> paramNamesEnumeration = request.getParameterNames();
    	while (paramNamesEnumeration.hasMoreElements()) {
            String key = paramNamesEnumeration.nextElement();
            if(includePage){
            	sb.append(key).append("=").append(request.getParameter(key)).append("&");
            }else{
	            if (!key.equals("page")) {
	                sb.append(key).append("=").append(request.getParameter(key)).append("&");
	            }
            }
        }
    	if(sb.length() > 0){
    		sb.deleteCharAt(sb.length() - 1);
    	}
    	return sb.toString();
    }
    
}
