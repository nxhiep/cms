package com.hiepnx.cms.shared.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.hiepnx.cms.shared.Config;

@Entity
public class Choice implements IBasic {

	@Ignore private static final long serialVersionUID = 1L;
	@Id private Long id;
	@Index private Long parentId = Config.LONG_NULL; // cardId
	@Index private int status = Config.STATUS_PUBLIC;
	private boolean correct = false;
	
	private String frontText = Config.TEXT_EMPTY;
	private String frontImage = Config.TEXT_EMPTY;
	private String frontSound = Config.TEXT_EMPTY;
	private String frontVideo = Config.TEXT_EMPTY;
	private String frontHint = Config.TEXT_EMPTY;

	@Index private Long lastUpdate;
	@Index private Long createDate;
	
	public Choice() {}
	
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

	public String getFrontText() {
		return frontText;
	}

	public void setFrontText(String frontText) {
		this.frontText = frontText;
	}

	public String getFrontImage() {
		return frontImage;
	}

	public void setFrontImage(String frontImage) {
		this.frontImage = frontImage;
	}

	public String getFrontSound() {
		return frontSound;
	}

	public void setFrontSound(String frontSound) {
		this.frontSound = frontSound;
	}

	public String getFrontVideo() {
		return frontVideo;
	}

	public void setFrontVideo(String frontVideo) {
		this.frontVideo = frontVideo;
	}

	public String getFrontHint() {
		return frontHint;
	}

	public void setFrontHint(String frontHint) {
		this.frontHint = frontHint;
	}
	
	public boolean isCorrect() {
		return correct;
	}
	
	public void setCorrect(boolean correct) {
		this.correct = correct;
	}
}
