package com.hiepnx.cms.shared.model;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.hiepnx.cms.shared.Config;

@Entity
public class Category implements IBasic {

	@Ignore private static final long serialVersionUID = 1L;
	@Id private Long id;
	private String name = Config.TEXT_EMPTY;
	private String description = Config.TEXT_EMPTY;
	@Index private int index = Config.INT_NULL;
	@Index private int type = Config.INT_NULL;
	@Index private Long parentId = Config.LONG_NULL;
	@Index private int status = Config.STATUS_PUBLIC;
	
	@Index private Long lastUpdate;
	@Index private Long createDate;
	@Ignore private List<Category> childCategories = new ArrayList<Category>();
	
	public Category() {}
	
	@Override
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public void setLastUpdate(Long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Long getLastUpdate() {
		return lastUpdate;
	}
	
	@Override
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public Long getCreateDate() {
		return createDate;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public List<Category> getChildCategories() {
		return childCategories;
	}
	
	public void setChildCategories(List<Category> childCategories) {
		this.childCategories = childCategories;
	}
}
