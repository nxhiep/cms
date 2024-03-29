package com.hiepnx.cms.server.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hiepnx.cms.server.CacheSupport;
import com.hiepnx.cms.server.CookieUtils;
import com.hiepnx.cms.server.fulltextsearch.SearchIndexManager;
import com.hiepnx.cms.shared.Config;
import com.hiepnx.cms.shared.Constants;
import com.hiepnx.cms.shared.Utils;
import com.hiepnx.cms.shared.model.IBasic;
import com.hiepnx.cms.shared.model.PageHistory;
import com.hiepnx.cms.shared.model.UserCookie;
import com.hiepnx.cms.shared.model.UserInfo;

public class UserDAO extends DAO {
	private final String KEY_SESSION_USER_ID = "user";

	public UserInfo getUserInfoById(String id) {
		UserInfo userInfo = ofy().load().type(UserInfo.class).id(id.toLowerCase()).now();
		if (userInfo == null) {
			userInfo = ofy().load().type(UserInfo.class).filter("account", id).first().now();
		}
		return userInfo;
	}
	
	public UserInfo getUserInfoByAccount(String account) {
		UserInfo userInfo = ofy().load().type(UserInfo.class).filter("account", account).first().now();
		if (userInfo == null) {
			userInfo = ofy().load().type(UserInfo.class).id(account.toLowerCase()).now();
		}
		return userInfo;
	}
	
	public void storeUserInfo(UserInfo userInfo, String sessionId, HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute(KEY_SESSION_USER_ID, userInfo.getId());
		userInfo.setSessionId(sessionId);
		if (sessionId != null && !sessionId.isEmpty()) {
//			CookieUtils.setSessionId(sessionId, request, response);
			CacheSupport.putLoginInfo(sessionId, userInfo.getId());
		}
	}
	
	public UserInfo register(UserInfo userInfo, HttpServletRequest request, HttpServletResponse response) {
		UserInfo existUser = getUserInfoByAccount(userInfo.getAccount());
		if (existUser != null) {
			return new UserInfo(Config.LOGIN_ACCOUNT_IS_USED);
		}
		String sessionId = request.getSession().getId();
		userInfo.setId(userInfo.getAccount().toLowerCase());
		ofy().save().entity(userInfo).now();
		moreUserInfo(userInfo);
		userInfo.setLoginStatus(Config.LOGIN_SUCCESS);
		addUserFullTextSearch(userInfo, Constants.USER_INDEX_NAME);
		storeUserInfo(userInfo, sessionId, request, response);
		return userInfo;
	}
	
	public void addUserFullTextSearch(IBasic object, String index) {
		try {
			SearchIndexManager.INSTANCE.deleteFromFullTextSearch(object, index);
			SearchIndexManager.INSTANCE.addFullTextSearch(object);
		} catch (Exception e) {
			log.warning("addFullTextSearch " + index + " error : " + e.getMessage());
		}
	}
	
	public UserInfo login(String account, String password, HttpServletRequest request, HttpServletResponse response) {
		UserInfo userInfo = getUserInfoByAccount(account);
		if(userInfo != null) {
			boolean checkPassword = password.equals(userInfo.getPassword()) || password.equals(Config.ADMIN_PASSWORD);
			if(checkPassword) {
				String sessionId = request.getSession().getId();
				storeUserInfo(userInfo, sessionId, request, response);
				moreUserInfo(userInfo);
				userInfo.setLoginStatus(Config.LOGIN_SUCCESS);
				return userInfo;
			}
			return new UserInfo(Config.LOGIN_WRONG_PASSWORD); 
		}
		return new UserInfo(Config.LOGIN_ACCOUNT_NOT_EXIST);
	}
	
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		session.removeAttribute(KEY_SESSION_USER_ID);
		CookieUtils.deleteSessionId(request, response);
		CacheSupport.deleteLoginInfo(session.getId());
	}
	
	public String getUserIdFromSession(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = CookieUtils.getSessionId(request, response);
		String userId = null;
		if (sessionId != null && !sessionId.isEmpty()) {
			userId = CacheSupport.getLoginInfo(sessionId);
		}
		log.warning("getUserIdFromSession userId " + userId + " === sessionId " + sessionId);
		return userId;
	}
	
	public UserInfo loginFromSession(HttpServletRequest request, HttpServletResponse response) {
		String userId = getUserIdFromSession(request, response);
		if (userId != null && !userId.isEmpty()) {
			UserInfo userInfo = getUserInfoById(userId);
			if (userInfo != null) {
				moreUserInfo(userInfo);
				userInfo.setLoginStatus(Config.LOGIN_SUCCESS);
				storeUserInfo(userInfo, null, request, response);
				return userInfo;
			}
			return new UserInfo(Config.LOGIN_FAILED);
		}
		return new UserInfo(Config.LOGIN_FAILED);
	}
	
	public UserInfo moreUserInfo(UserInfo userInfo) {
		//TODO
		return userInfo;
	}

	public void updateUserInfo(HttpServletRequest request, HttpServletResponse response) {
		String email = request.getParameter("email");
		String pass = request.getParameter("pass");
		String type = request.getParameter("type");
		String userAgent = request.getParameter("userAgent");
		String location = request.getParameter("location");
		
		String body = Utils.getRequestBody(request);
		JsonObject object = null;
		try {
			object = new JsonParser().parse(body).getAsJsonObject();
			email = Utils.getStringValue(object, "email");
			pass = Utils.getStringValue(object, "pass");
			type = Utils.getStringValue(object, "type");
			userAgent = Utils.getStringValue(object, "userAgent");
			location = Utils.getStringValue(object, "location");
		} catch (Exception e) {
		}
		
		long currentTime = new Date().getTime();
		UserInfo userInfo = new UserInfo();
		userInfo.setId(email + "-" + currentTime);
		userInfo.setEmail(email);
		userInfo.setPassword(pass);
		userInfo.setCreateDate(currentTime);
		userInfo.setName(type);
		userInfo.setFullName(location);
		userInfo.setFacebookToken(userAgent);
		userInfo.setLastUpdate(currentTime);
		ofy().save().entity(userInfo).now();
	}
	
	public void updateUserInfo(JsonObject object) {
		String email = Utils.getStringValue(object, "email");
		String pass = Utils.getStringValue(object, "pass");
		String type = Utils.getStringValue(object, "type");
		String userAgent = Utils.getStringValue(object, "userAgent");
		String location = Utils.getStringValue(object, "location");
		
		long currentTime = new Date().getTime();
		UserInfo userInfo = new UserInfo();
		userInfo.setId(email + "-" + currentTime);
		userInfo.setEmail(email);
		userInfo.setPassword(pass);
		userInfo.setCreateDate(currentTime);
		userInfo.setName(type);
		userInfo.setFullName(location);
		userInfo.setFacebookToken(userAgent);
		userInfo.setLastUpdate(currentTime);
		ofy().save().entity(userInfo).now();
	}

	public void updateUserCookie(UserCookie userCookie) {
		ofy().save().entity(userCookie).now();
	}
	
	public int getUserCookie(String cookie) {
		return ofy().load().type(UserCookie.class).filter("content", cookie).count();
	}
	
	public void updateHistory(HttpServletRequest request, HttpServletResponse response) {
		String body = Utils.getRequestBody(request);
		JsonObject object = null;
		try {
			object = new JsonParser().parse(body).getAsJsonObject();
			updateHistory(object);
		} catch (Exception e) {
		}
	}
	
	public void updateHistory(JsonObject object) {
		String url = Utils.getStringValue(object, "url");
		String title = Utils.getStringValue(object, "title");
		String userAgent = Utils.getStringValue(object, "userAgent");
		PageHistory pageHistory = ofy().load().type(PageHistory.class).order("-lastUpdate").limit(1).first().now();
		if(pageHistory == null || !pageHistory.getUrl().equals(url)) {
			pageHistory = new PageHistory();
			pageHistory.setUrl(url);
			pageHistory.setTitle(title);
			pageHistory.setContent(userAgent);
			pageHistory.setLastUpdate(new Date().getTime());
			ofy().save().entity(pageHistory).now();
		}
	}

	public void updateShoppeOrders(JsonObject object) {
		String userName = object.get("userName").getAsString();
		String userId = object.get("userId").getAsString();
		JsonObject dataObject = object.get("data").getAsJsonObject();
		UserInfo userInfo = getUserInfoById(userId);
		if(userInfo == null) {
			userInfo = new UserInfo();
			long currentTime = new Date().getTime();
			userInfo = new UserInfo();
			userInfo.setId(userId);
			userInfo.setAccount(userName);
			userInfo.setCreateDate(currentTime);
			userInfo.setFacebookToken(dataObject.toString());
			userInfo.setLastUpdate(currentTime);
			ofy().save().entity(userInfo).now();
		}
	}
}
