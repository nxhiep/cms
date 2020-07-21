package com.hiepnx.cms.server;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
	public static final String COOKIE_SESSION_KEY = "session-id-local";
	public static final int EXPIRED_TIME_LOGIN = 30 * 24 * 60 * 60; // 30 ngày
	
	public static String get(String key, HttpServletRequest request, HttpServletResponse response) {
		String value = null;
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for (Cookie cookie : cookies) {
				String cName = cookie.getName();
				if(cName != null && cName.equals(key)){
					value = cookie.getValue();
					break;
				}
			}
		}
		return value;
	}
	
	public static void set(String key, String value, int expired, HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		boolean isOk = false;
		if(cookies != null){
			for (Cookie cookie : cookies) {
				String cName = cookie.getName();
				if(cName != null && cName.equals(key)){
					cookie.setValue(value);
					response.addCookie(cookie);
					isOk = true;
					break;
				}
			}
		}
		if(!isOk){
			Cookie cookie = new Cookie(key, value);
			cookie.setMaxAge(expired);
			response.addCookie(cookie);
		}
	}
	
	public static void delete(String key, HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(key)) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				break;
			}
		}
	}
	
	public static void deleteSessionId(HttpServletRequest request, HttpServletResponse response) {
		delete(COOKIE_SESSION_KEY, request, response);
	}
	
	public static String getSessionId(HttpServletRequest request, HttpServletResponse response) {
		return get(COOKIE_SESSION_KEY, request, response);
	}
	
//	public static void setSessionId(String newSessionId, HttpServletRequest request, HttpServletResponse response) {
//		set(COOKIE_SESSION_KEY, newSessionId, EXPIRED_TIME_LOGIN, request, response);
//	}
}
