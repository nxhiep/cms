package com.hiepnx.cms.server;

import java.io.Serializable;
import java.util.logging.Logger;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceException;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.hiepnx.cms.shared.Config;

public class CacheSupport {

	protected static final Logger log = Logger.getLogger(CacheSupport.class
			.getName());

	public static final String ACCOUNT_SPACE = "loginsession";
	public static final String LOGIN_NAME_SPACE = "loginsession";
	public static final String ATOMICITY = "atomicity";

	private static MemcacheService cacheInit(String nameSpace) {
		MemcacheService memcache = MemcacheServiceFactory
				.getMemcacheService(nameSpace);
		return memcache;
	}

	public static Object cacheGet(String nameSpace, Object id) {
		Object r = null;
		MemcacheService memcache = cacheInit(nameSpace);
		try {
			r = memcache.get(id);
		} catch (MemcacheServiceException e) {
			e.printStackTrace();
		}
		return r;
	}

	public static void cacheDelete(String nameSpace, Object id) {
		MemcacheService memcache = cacheInit(nameSpace);
		memcache.delete(id);
	}

	public static void cachePutExp(String nameSpace, Object id,
			Serializable o, int exp) {
		MemcacheService memcache = cacheInit(nameSpace);
		try {
			if (exp > 0) {
				memcache.put(id, o, Expiration.byDeltaSeconds(exp));
			} else {
				memcache.put(id, o);
			}
		} catch (MemcacheServiceException e) {
			e.printStackTrace();
		}
	}

	public static void cachePut(String nameSpace, Object id, Serializable o) {
		int expiredSeconds = Config.DEFAULT_EXPIRED_TIME * 24 * 60 * 60;
		cachePutExp(nameSpace, id, o, expiredSeconds);
	}

	public static void reset() {
		try {
			MemcacheService memcache = cacheInit(LOGIN_NAME_SPACE);
			memcache.clearAll();
		} catch (Exception e) {
		}
	}

	public static void increment(Object key, long delta) {
		MemcacheService memcache = cacheInit(ATOMICITY);
		memcache.increment(key, delta, 0L);
	}

	public static Object get(String nameSpace, Object key) {
		MemcacheService memcache = cacheInit(nameSpace);
		return memcache.get(key);
	}

	/********************* LOGIN ********************/
	public static String getLoginInfo(String sessionId) {
		return (String) cacheGet(LOGIN_NAME_SPACE, sessionId);
	}
	
	public static void putLoginInfo(String sessionId, String userId) {
		cachePut(LOGIN_NAME_SPACE, sessionId,userId);
	}

	public static void deleteLoginInfo(String sessionId) {
		cacheDelete(LOGIN_NAME_SPACE, sessionId);
	}
}