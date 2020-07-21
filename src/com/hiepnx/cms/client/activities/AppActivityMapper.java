package com.hiepnx.cms.client.activities;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.hiepnx.cms.client.activities.basic.BasicActivity;
import com.hiepnx.cms.client.activities.category.CategoryActivity;
import com.hiepnx.cms.client.activities.category.CategoryPlace;
import com.hiepnx.cms.client.activities.home.HomeActivity;
import com.hiepnx.cms.client.activities.home.HomePlace;

public class AppActivityMapper implements AsyncActivityMapper {

	private ClientFactory clientFactory;

	public AppActivityMapper(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void getActivity(final Place place, final ActivityCallbackHandler activityCallbackHandler) {
		if (place instanceof HomePlace) {
			runAsync(activityCallbackHandler, new HomeActivity(place, clientFactory));
		} else if (place instanceof CategoryPlace) {
			runAsync(activityCallbackHandler, new CategoryActivity(place, clientFactory));
		}
	}

	private void runAsync(final ActivityCallbackHandler activityCallbackHandler, final BasicActivity activity) {
		GWT.runAsync(new RunAsyncCallback() {

			@Override
			public void onFailure(Throwable err) {
				Window.alert("Vui lòng tải lại trang này!");
			}

			@Override
			public void onSuccess() {
				activityCallbackHandler.onRecieveActivity(activity);
			}
		});
	}
}
