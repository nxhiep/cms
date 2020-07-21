package com.hiepnx.cms.client.activities;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;
import com.hiepnx.cms.client.activities.category.CategoryView;
import com.hiepnx.cms.client.activities.category.CategoryViewImpl;
import com.hiepnx.cms.client.activities.home.HomeView;
import com.hiepnx.cms.client.activities.home.HomeViewImpl;

public class ClientFactoryImpl implements ClientFactory {
	private SimpleEventBus eventBus;
	private PlaceController placeController;
	
	public ClientFactoryImpl() {
		eventBus = new SimpleEventBus();
		placeController = new PlaceController(eventBus);
	}
	
	@Override
	public PlaceController getPlaceController() {
		return placeController;
	}

	@Override
	public EventBus getEventBus() {
		return eventBus;
	}

	@Override
	public HomeView getHomeView() {
		return new HomeViewImpl();
	}

	@Override
	public CategoryView getCategoryView() {
		return new CategoryViewImpl();
	}
}
