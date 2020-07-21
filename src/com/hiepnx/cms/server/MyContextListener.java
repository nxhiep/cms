package com.hiepnx.cms.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.googlecode.objectify.ObjectifyService;
import com.hiepnx.cms.shared.model.Card;
import com.hiepnx.cms.shared.model.CardProgress;
import com.hiepnx.cms.shared.model.Category;
import com.hiepnx.cms.shared.model.CategoryProgress;
import com.hiepnx.cms.shared.model.UserInfo;

public class MyContextListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
//		ObjectifyService.register(Card.class);
		ObjectifyService.register(CardProgress.class);
		ObjectifyService.register(Category.class);
		ObjectifyService.register(CategoryProgress.class);
		ObjectifyService.register(UserInfo.class);
	}
}
