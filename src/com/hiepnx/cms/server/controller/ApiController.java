package com.hiepnx.cms.server.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiepnx.cms.shared.model.UserInfo;

@Controller
@RequestMapping(value = "/api")
public class ApiController extends BasicController {
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody UserInfo login(@RequestParam("account") String account, @RequestParam("password") String password,
			HttpServletRequest request, HttpServletResponse response) {
		return USER_DAO.login(account, password, request, response);
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody UserInfo register(@RequestParam("account") String account, @RequestParam("password") String password,
			@RequestParam("email") String email, @RequestParam("phoneNumber") String phoneNumber,
			HttpServletRequest request, HttpServletResponse response) {
		final UserInfo newUserInfo = new UserInfo();
		newUserInfo.setAccount(account);
		newUserInfo.setPassword(password);
		newUserInfo.setEmail(email);
		newUserInfo.setPhoneNumber(phoneNumber);
		return USER_DAO.register(newUserInfo, request, response);
	}
	
	@RequestMapping(value="/logout", method = RequestMethod.POST)
	public @ResponseBody void logout(HttpServletRequest request, HttpServletResponse response) {
		USER_DAO.logout(request, response);
	}
}
