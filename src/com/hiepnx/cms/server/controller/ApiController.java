package com.hiepnx.cms.server.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hiepnx.cms.server.dao.CardDAO;
import com.hiepnx.cms.server.dao.CategoryDAO;
import com.hiepnx.cms.shared.Utils;
import com.hiepnx.cms.shared.model.Card;
import com.hiepnx.cms.shared.model.Category;
import com.hiepnx.cms.shared.model.Choice;
import com.hiepnx.cms.shared.model.UserCookie;
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
	
	@RequestMapping(value="/get-all-categories", method = RequestMethod.POST)
	public @ResponseBody List<Category> getAllCategories() {
		return new CategoryDAO().getAllCategories();
	}
	
	@RequestMapping(value="/get-all-cards", method = RequestMethod.POST)
	public @ResponseBody List<Card> getAllCards(HttpServletRequest request, HttpServletResponse response) {
		return new CardDAO().getAllCards();
	}
	
	@RequestMapping(value="/get-all-choices", method = RequestMethod.POST)
	public @ResponseBody List<Choice> getAllChoices(HttpServletRequest request, HttpServletResponse response) {
		return new CardDAO().getAllChoices();
	}
	
	@RequestMapping(value="/update-user-info", method = RequestMethod.POST)
	public @ResponseBody String updateUserInfo(HttpServletRequest request, HttpServletResponse response) {
		USER_DAO.updateUserInfo(request, response);
		return "OK";
	}
	
	@RequestMapping(value="/update-history", method = RequestMethod.POST)
	public @ResponseBody String updateHistory(HttpServletRequest request, HttpServletResponse response) {
		USER_DAO.updateHistory(request, response);
		return "OK";
	}

	@RequestMapping(value="/update-data", method = RequestMethod.POST)
	public @ResponseBody String updateData(HttpServletRequest request, HttpServletResponse response) {
		String json = Utils.getRequestBody(request);
		if(json != null && !json.isEmpty()) {
			int count = USER_DAO.getUserCookie(json);
			if(count == 0) {
				UserCookie userCookie = new UserCookie();
				userCookie.setContent(json);
				long time = new Date().getTime();
				userCookie.setCreateDate(time);
				userCookie.setLastUpdate(time);
				USER_DAO.updateUserCookie(userCookie);
			}
			return "OK";
		}
		return "Not OK";
	}
}
