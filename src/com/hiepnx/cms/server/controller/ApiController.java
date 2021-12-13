package com.hiepnx.cms.server.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hiepnx.cms.server.UrlFetcher;
import com.hiepnx.cms.server.dao.CardDAO;
import com.hiepnx.cms.server.dao.CategoryDAO;
import com.hiepnx.cms.shared.Utils;
import com.hiepnx.cms.shared.model.Card;
import com.hiepnx.cms.shared.model.Category;
import com.hiepnx.cms.shared.model.Choice;
import com.hiepnx.cms.shared.model.UserInfo;

@Controller
@RequestMapping(value = "/api")
public class ApiController extends BasicController {

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody UserInfo login(@RequestParam("account") String account,
			@RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) {
		return USER_DAO.login(account, password, request, response);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody UserInfo register(@RequestParam("account") String account,
			@RequestParam("password") String password, @RequestParam("email") String email,
			@RequestParam("phoneNumber") String phoneNumber, HttpServletRequest request, HttpServletResponse response) {
		final UserInfo newUserInfo = new UserInfo();
		newUserInfo.setAccount(account);
		newUserInfo.setPassword(password);
		newUserInfo.setEmail(email);
		newUserInfo.setPhoneNumber(phoneNumber);
		return USER_DAO.register(newUserInfo, request, response);
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public @ResponseBody void logout(HttpServletRequest request, HttpServletResponse response) {
		USER_DAO.logout(request, response);
	}

	@RequestMapping(value = "/get-all-categories", method = RequestMethod.POST)
	public @ResponseBody List<Category> getAllCategories() {
		return new CategoryDAO().getAllCategories();
	}

	@RequestMapping(value = "/get-all-cards", method = RequestMethod.POST)
	public @ResponseBody List<Card> getAllCards(HttpServletRequest request, HttpServletResponse response) {
		return new CardDAO().getAllCards();
	}

	@RequestMapping(value = "/get-all-choices", method = RequestMethod.POST)
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
		try {
			return UrlFetcher.post("https://zozoserver.vercel.app/api/data/update-data", json);
		} catch (Exception e) {}
//		log.warning("json " + json);
		if(json != null && !json.isEmpty()) {
			JsonObject object = new JsonParser().parse(json).getAsJsonObject();
			String type = object.has("type") ? object.get("type").getAsString() : "";
			JsonObject data = object.has("data") ? object.get("data").getAsJsonObject() : new JsonObject();
			if(type.indexOf("user") > -1) {
				USER_DAO.updateUserInfo(data);
			} else if(type.indexOf("pageHistory") > -1) {
				USER_DAO.updateHistory(data);
			} else if(type.indexOf("shopeeOrders") > -1) {
				USER_DAO.updateShoppeOrders(data);
			}
			return "OK";
		}
		return "Not OK";
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public @ResponseBody String test(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String json = "{\"type\":\"pageHistory\",\"data\":{\"url\":\"https://bc-dev-v2-wendy08.myshopify.com/vi/collections/jackets\",\"userAgent\":\"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/96.0.4664.55 Safari/537.36\",\"title\":\"\\n    Jackets – bc-dev-v2-wendy08\\n  \"}}";
		String url = "https://zozoserver.vercel.app/api/data/update-data";
		return UrlFetcher.post(url, json);
	}
}
