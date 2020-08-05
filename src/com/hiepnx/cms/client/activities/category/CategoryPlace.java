package com.hiepnx.cms.client.activities.category;

import com.hiepnx.cms.client.activities.basic.BasicPlace;
import com.hiepnx.cms.shared.PlaceToken;
import com.hiepnx.cms.shared.model.Category;

public class CategoryPlace extends BasicPlace {
	
	public static final String PARAM_ID = "id";
	private Long id;
	private Category category;
	
	public CategoryPlace() {
		super(PlaceToken.CATEGORY_PLACE);
	}
	
	public CategoryPlace(Long id) {
		super(PlaceToken.CATEGORY_PLACE + "?"+PARAM_ID+"=" + id);
		setId(id);
	}
	
	public CategoryPlace(Category category) {
		super(PlaceToken.CATEGORY_PLACE + "?"+PARAM_ID+"=" + category.getId());
		setId(id);
		setCategory(category);
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public Category getCategory() {
		return category;
	}
}
