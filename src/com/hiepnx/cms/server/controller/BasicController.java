package com.hiepnx.cms.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;

import com.hiepnx.cms.server.dao.UserDAO;
import com.hiepnx.cms.shared.model.UserInfo;

public class BasicController {
	protected UserDAO USER_DAO = new UserDAO();
	
	protected UserInfo getCurrentUserInfoBySession(Model model, HttpServletResponse response, HttpServletRequest request) {
		UserInfo currentUser = USER_DAO.loginFromSession(request, response);
		if(currentUser != null && (currentUser.getId() == null || currentUser.getId().isEmpty())) {
			return null;
		}
		model.addAttribute("currentUser", currentUser);
		return currentUser;
	}
	
	public void send301Redirect(HttpServletResponse response, String newUrl) {
        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        response.setHeader("Location", newUrl);
        response.setHeader("Connection", "close");
    }
}
