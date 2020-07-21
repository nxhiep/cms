package com.hiepnx.cms.server.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.hiepnx.cms.shared.model.Category;

public class CategoryDAO extends DAO {
	
	public List<Category> getCategoriesByParentId(Long parentId) {
		List<Category> categories = new ArrayList<Category>(ofy().load().type(Category.class).filter("parentId", parentId).list());
		for (Category category : categories) {
			category.setChildCategories(getCategoriesByParentId(category.getId()));
		}
		return categories;
	}
}
