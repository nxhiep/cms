package com.hiepnx.cms.shared.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.hiepnx.cms.shared.Config;

@Entity
public class CategoryProgress implements IBasic {

	@Ignore private static final long serialVersionUID = 1L;
	@Id private String id;
	@Index private String userId = Config.TEXT_EMPTY;
	@Index private Long parentId = Config.NULL_ID;
	@Index private int progress = 0;
	@Index private int status = Config.STATUS_PUBLIC;

	@Index private Long lastUpdate;
	@Index private Long createDate;
	
	public CategoryProgress() {}
	
	@Override
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}
}
