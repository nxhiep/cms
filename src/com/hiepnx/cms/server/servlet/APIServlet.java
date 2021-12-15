package com.hiepnx.cms.server.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hiepnx.cms.server.UrlFetcher;
import com.hiepnx.cms.server.controller.BasicController;
import com.hiepnx.cms.shared.Utils;

public class APIServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	protected final Logger log = Logger.getLogger(BasicController.class.getName());

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Access-Control-Allow-Origin", "*");
		String json = Utils.getRequestBody(req);
		String url = "https://zozoserver.vercel.app/api/data/update-data";
		String data = "";
		try {
			data = UrlFetcher.onPostData(url, json);
		} catch (Exception e) {
			data = "ERROR: " + e.getMessage();
		}
		log.warning("data " + data);
		responseData(data, resp);
	}
	
	private void responseData(Object data, HttpServletResponse resp) throws IOException {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter writer = resp.getWriter();
		writer.print(data);
		writer.flush();
	}
}
