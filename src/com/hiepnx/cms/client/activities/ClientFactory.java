package com.hiepnx.cms.client.activities;

import com.google.gwt.place.shared.PlaceController;
import com.google.web.bindery.event.shared.EventBus;
import com.hiepnx.cms.client.activities.category.CategoryView;
import com.hiepnx.cms.client.activities.home.HomeView;

public interface ClientFactory {
	PlaceController getPlaceController();
	EventBus getEventBus();
	HomeView getHomeView();
	CategoryView getCategoryView();
}
