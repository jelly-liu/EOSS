package com.jelly.eoss.util;

import com.google.gson.Gson;

/**
 * Created by jelly on 2016-10-25.
 */
public class JsonUtil {
    public static <T> String toJson(T t){
        return new Gson().toJson(t);
    }

    public static <T> T toObject(String json, Class cls){
        return (T) new Gson().fromJson(json, cls);
    }
}
