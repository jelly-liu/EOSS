package com.jelly.eoss.util;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;

public class Log {
	private static String pre = "";

	private static Map<String, Logger> map = new TreeMap<String, Logger>();

	public static void Info(Serializable obj) {
		String logName = Log.class.getName();
		if (!map.containsKey(logName)) {
			map.put(logName, Logger.getLogger(logName));
		}

		map.get(Log.class.getName()).info(pre + (obj == null ? "null" : obj.toString()));
	}

	public static void Info(Serializable obj, Class<?> c) {
		if (!map.containsKey(c.getName())) {
			map.put(c.getName(), Logger.getLogger(c.getName()));
		}

		map.get(c.getName()).info(pre + (obj == null ? "null" : obj.toString()));
	}
	
	public static void Debug(Serializable obj) {
		String logName = Log.class.getName();
		if (!map.containsKey(logName)) {
			map.put(logName, Logger.getLogger(logName));
		}

		map.get(Log.class.getName()).debug(pre + (obj == null ? "null" : obj.toString()));
	}

	public static void Debug(Serializable obj, Class<?> c) {
		if (!map.containsKey(c.getName())) {
			map.put(c.getName(), Logger.getLogger(c.getName()));
		}

		map.get(c.getName()).debug(pre + (obj == null ? "null" : obj.toString()));
	}
	
	public static void Error(Serializable obj) {
		String logName = Log.class.getName();
		if (!map.containsKey(logName)) {
			map.put(logName, Logger.getLogger(logName));
		}

		map.get(Log.class.getName()).error(pre + (obj == null ? "null" : obj.toString()));
	}

	public static void Error(Serializable obj, Class<?> c) {
		if (!map.containsKey(c.getName())) {
			map.put(c.getName(), Logger.getLogger(c.getName()));
		}

		map.get(c.getName()).error(pre + (obj == null ? "null" : obj.toString()));
	}
}
