package com.hiepnx.cms.client.activities.basic;

import com.google.gwt.place.shared.Place;

public class BasicPlace extends Place {

	protected String token = "";
	
	public BasicPlace() {
	}
	
	public BasicPlace(String token) {
		this();
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}
}
