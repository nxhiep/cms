package com.hiepnx.cms.client.activities;

import com.google.gwt.place.shared.Place;

public interface AsyncActivityMapper{
	void getActivity(Place place, ActivityCallbackHandler activityCallbackHandler);
}
