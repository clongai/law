package com.law.common;

import java.util.concurrent.ConcurrentHashMap;

public class Session {

	private final static ConcurrentHashMap<String, String> session = new ConcurrentHashMap<>();
	
	public static void setAttribute(String key) {
		session.put(key, key);
	}

	
	public static String getAttribute(String key) {
		return session.get(key);
	}
	
	public static boolean exist(String key) {
		return session.containsKey(key);
	}
}
