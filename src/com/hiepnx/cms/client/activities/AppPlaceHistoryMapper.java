package com.hiepnx.cms.client.activities;

import java.util.Map;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.hiepnx.cms.client.ClientUtils;
import com.hiepnx.cms.client.activities.basic.BasicPlace;
import com.hiepnx.cms.client.activities.category.CategoryPlace;
import com.hiepnx.cms.client.activities.home.HomePlace;
import com.hiepnx.cms.shared.PlaceToken;

public class AppPlaceHistoryMapper implements PlaceHistoryMapper {

	@Override
	public Place getPlace(String token) {
		Map<String, String> params = ClientUtils.getMapURLSearchParams(token);
		if(token.contains(PlaceToken.CATEGORY_PLACE)) {
			Long id = -1l;
			if(params.containsKey(CategoryPlace.PARAM_ID)) {
				id = Long.parseLong(params.get(CategoryPlace.PARAM_ID));
			}
			return new CategoryPlace(id, params.get(CategoryPlace.PARAM_NAME));
		}
		return new HomePlace();
	}

	@Override
	public String getToken(Place place) {
		return ((BasicPlace) place).getToken();
	}
}
