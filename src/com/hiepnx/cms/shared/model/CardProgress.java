package com.hiepnx.cms.shared.model;

import java.util.ArrayList;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.hiepnx.cms.shared.Config;

@Entity
public class CardProgress implements IBasic {
	
	@Ignore private static final long serialVersionUID = 1L;
	@Id private String id;// userId_cardId_couserId
	@Index private String userId;
	@Index private Long cardId = Config.NULL_ID;
	@Index private Long parentId = Config.NULL_ID;
	@Index private int status = Config.STATUS_PUBLIC;
	@Index private int cardType = 0;
	private int boxNum = 1;
	private int difficultyLevel = 0;
	private ArrayList<Integer> history = new ArrayList<Integer>();
	private int progress = 0;
	@Index private Long lastUpdate;
	@Index private Long createDate;

	public CardProgress() {}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
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

	public int getCardType() {
		return cardType;
	}

	public void setCardType(int cardType) {
		this.cardType = cardType;
	}

	public int getBoxNum() {
		return boxNum;
	}

	public void setBoxNum(int boxNum) {
		this.boxNum = boxNum;
	}

	public int getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(int difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
	}

	public ArrayList<Integer> getHistory() {
		return history;
	}

	public void setHistory(ArrayList<Integer> history) {
		this.history = history;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public long getLastUpdate() {
		return lastUpdate;
	}

	@Override
	public void setLastUpdate(Long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public long getCreateDate() {
		return createDate;
	}
	
	@Override
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}
}