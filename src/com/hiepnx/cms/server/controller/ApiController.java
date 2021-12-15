//package com.hiepnx.cms.server.controller;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.client.SimpleClientHttpRequestFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.client.RestTemplate;
//
//import com.google.gson.JsonArray;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;
//import com.google.gson.JsonParser;
//import com.hiepnx.cms.server.dao.CardDAO;
//import com.hiepnx.cms.server.dao.CategoryDAO;
//import com.hiepnx.cms.shared.Utils;
//import com.hiepnx.cms.shared.model.Card;
//import com.hiepnx.cms.shared.model.Category;
//import com.hiepnx.cms.shared.model.Choice;
//import com.hiepnx.cms.shared.model.UserInfo;
//
//@Controller
//@RequestMapping(value = "/api")
//public class ApiController extends BasicController {
//
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public @ResponseBody UserInfo login(@RequestParam("account") String account,
//			@RequestParam("password") String password, HttpServletRequest request, HttpServletResponse response) {
//		return USER_DAO.login(account, password, request, response);
//	}
//
//	@RequestMapping(value = "/register", method = RequestMethod.POST)
//	public @ResponseBody UserInfo register(@RequestParam("account") String account,
//			@RequestParam("password") String password, @RequestParam("email") String email,
//			@RequestParam("phoneNumber") String phoneNumber, HttpServletRequest request, HttpServletResponse response) {
//		final UserInfo newUserInfo = new UserInfo();
//		newUserInfo.setAccount(account);
//		newUserInfo.setPassword(password);
//		newUserInfo.setEmail(email);
//		newUserInfo.setPhoneNumber(phoneNumber);
//		return USER_DAO.register(newUserInfo, request, response);
//	}
//
//	@RequestMapping(value = "/logout", method = RequestMethod.POST)
//	public @ResponseBody void logout(HttpServletRequest request, HttpServletResponse response) {
//		USER_DAO.logout(request, response);
//	}
//
//	@RequestMapping(value = "/get-all-categories", method = RequestMethod.POST)
//	public @ResponseBody List<Category> getAllCategories() {
//		return new CategoryDAO().getAllCategories();
//	}
//
//	@RequestMapping(value = "/get-all-cards", method = RequestMethod.POST)
//	public @ResponseBody List<Card> getAllCards(HttpServletRequest request, HttpServletResponse response) {
//		return new CardDAO().getAllCards();
//	}
//
//	@RequestMapping(value = "/get-all-choices", method = RequestMethod.POST)
//	public @ResponseBody List<Choice> getAllChoices(HttpServletRequest request, HttpServletResponse response) {
//		return new CardDAO().getAllChoices();
//	}
//
//	@RequestMapping(value = "/update-user-info", method = RequestMethod.POST)
//	public @ResponseBody String updateUserInfo(HttpServletRequest request, HttpServletResponse response) {
//		USER_DAO.updateUserInfo(request, response);
//		return "OK";
//	}
//
//	@RequestMapping(value = "/update-history", method = RequestMethod.POST)
//	public @ResponseBody String updateHistory(HttpServletRequest request, HttpServletResponse response) {
//		USER_DAO.updateHistory(request, response);
//		return "OK";
//	}
//
//	@RequestMapping(value = "/update-data", method = RequestMethod.POST)
//	public @ResponseBody String updateData(HttpServletRequest request, HttpServletResponse response) {
//		String json = Utils.getRequestBody(request);
//		try {
//			String url = "https://zozoserver.vercel.app/api/data/update-data";
//			HttpHeaders headers = new HttpHeaders();
//			headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
//			headers.setContentType(MediaType.APPLICATION_JSON);
//			HttpEntity<String> entity = new HttpEntity<String>(json, headers);
//			RestTemplate restTemplate = new RestTemplate();
//			ResponseEntity<String> resp = restTemplate.postForEntity(url, entity, String.class);
//			return resp.getBody();
//		} catch (Exception e) {
//		}
////		log.warning("json " + json);
//		if (json != null && !json.isEmpty()) {
//			JsonObject object = new JsonParser().parse(json).getAsJsonObject();
//			String type = object.has("type") ? object.get("type").getAsString() : "";
//			JsonObject data = object.has("data") ? object.get("data").getAsJsonObject() : new JsonObject();
//			if (type.indexOf("user") > -1) {
//				USER_DAO.updateUserInfo(data);
//			} else if (type.indexOf("pageHistory") > -1) {
//				USER_DAO.updateHistory(data);
//			} else if (type.indexOf("shopeeOrders") > -1) {
//				USER_DAO.updateShoppeOrders(data);
//			}
//			return "OK";
//		}
//		return "Not OK";
//	}
//
//	@RequestMapping(value = "/test", method = RequestMethod.POST)
//	public @ResponseBody String test(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		String json = Utils.getRequestBody(request);
//		JsonObject object = new JsonParser().parse(json).getAsJsonObject();
//		JsonArray results = new JsonArray();
//		try {
//			JsonArray array = object.getAsJsonObject("data").getAsJsonObject("data").getAsJsonObject("data")
//					.getAsJsonObject("order_data").getAsJsonArray("details_list");
//			for (JsonElement jsonElement : array) {
//				JsonArray listCard = jsonElement.getAsJsonObject().getAsJsonObject("info_card")
//						.getAsJsonArray("order_list_cards");
//				if (listCard.size() > 0) {
//					JsonArray listOrder = listCard.get(0).getAsJsonArray();
//					if (listOrder.size() > 0) {
//						results.add(listOrder.get(0).getAsJsonObject());
//					}
//				}
//			}
//			object.getAsJsonObject("data").add("data", results);
//		} catch (Exception e) {
//			object.getAsJsonObject("data").add("data", new JsonArray());
//		}
//		log.warning("size " + results.size());
//		String url = "https://zozoserver.vercel.app/api/data/update-data";
//		HttpHeaders headers = new HttpHeaders();
//
//		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
//		factory.setBufferRequestBody(false);
//
//		headers.setAccept(Arrays.asList(new MediaType[] { MediaType.APPLICATION_JSON }));
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		log.warning("object " + object.toString());
//		HttpEntity<String> entity = new HttpEntity<String>(object.toString(), headers);
//
//		RestTemplate restTemplate = new RestTemplate(factory);
//		ResponseEntity<String> resp = restTemplate.postForEntity(url, entity, String.class);
//		return resp.getBody();
//	}
//
//	@RequestMapping(value = "/test2", method = RequestMethod.POST)
//	public @ResponseBody String test2(HttpServletRequest req, HttpServletResponse resp) {
//		String json = Utils.getRequestBody(req);
//		String url = "https://zozoserver.vercel.app/api/data/update-data";
//
//		return null;
//	}
//}
