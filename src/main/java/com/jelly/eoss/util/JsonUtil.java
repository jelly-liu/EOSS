package com.jelly.eoss.util;

import com.alibaba.fastjson.JSON;

/**
 * Created by jelly on 2016-10-25.
 */
public class JsonUtil {
    public static String toJson(Object t){
        return JSON.toJSONString(t);
    }

    public static <T> T toObject(String json, Class cls){
        return (T)JSON.parseObject(json, cls);
    }
}
