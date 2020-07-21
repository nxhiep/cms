package com.hiepnx.cms.client.activities.category;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Widget;
import com.hiepnx.cms.client.activities.basic.BasicViewImpl;

public class CategoryViewImpl extends BasicViewImpl implements CategoryView {

	private static CategoryViewImplUiBinder uiBinder = GWT.create(CategoryViewImplUiBinder.class);

	interface CategoryViewImplUiBinder extends UiBinder<Widget, CategoryViewImpl> {
	}

	@UiField Button buttonHome;
	
	public CategoryViewImpl() {
		super();
		setView(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public HasClickHandlers getButtonHome() {
		return buttonHome;
	}
}
