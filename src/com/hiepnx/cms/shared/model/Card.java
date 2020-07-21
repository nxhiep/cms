package com.hiepnx.cms.shared.model;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Ignore;
import com.googlecode.objectify.annotation.Index;
import com.hiepnx.cms.shared.Config;

@Entity
public class Card implements IBasic {

	@Ignore private static final long serialVersionUID = 1L;
	@Id private Long id;
	@Index private int index = Config.INT_NULL;
	@Index private int difficultyLevel = Config.INT_NULL;
	@Index private int type = Config.INT_NULL;
	@Index private Long parentId = Config.LONG_NULL;
	@Index private int status = Config.STATUS_PUBLIC;
	@Index private String code = Config.TEXT_EMPTY;
	
	private String frontText = Config.TEXT_EMPTY;
	private String frontImage = Config.TEXT_EMPTY;
	private String frontSound = Config.TEXT_EMPTY;
	private String frontVideo = Config.TEXT_EMPTY;
	private String frontHint = Config.TEXT_EMPTY;

	private String backText = Config.TEXT_EMPTY;
	private String backImage = Config.TEXT_EMPTY;
	private String backSound = Config.TEXT_EMPTY;
	private String backVideo = Config.TEXT_EMPTY;
	private String backHint = Config.TEXT_EMPTY;
	
	private List<Long> choiceIds = new ArrayList<Long>();
	
	@Index private Long lastUpdate;
	@Index private Long createDate;
	
	@Ignore private List<Card> childCards =new ArrayList<Card>();
	@Ignore private List<Choice> choices =new ArrayList<Choice>();
	
	public Card() {}
	
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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getDifficultyLevel() {
		return difficultyLevel;
	}

	public void setDifficultyLevel(int difficultyLevel) {
		this.difficultyLevel = difficultyLevel;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getBackText() {
		return backText;
	}

	public void setBackText(String backText) {
		this.backText = backText;
	}

	public String getBackImage() {
		return backImage;
	}

	public void setBackImage(String backImage) {
		this.backImage = backImage;
	}

	public String getBackSound() {
		return backSound;
	}

	public void setBackSound(String backSound) {
		this.backSound = backSound;
	}

	public String getBackVideo() {
		return backVideo;
	}

	public void setBackVideo(String backVideo) {
		this.backVideo = backVideo;
	}

	public String getBackHint() {
		return backHint;
	}

	public void setBackHint(String backHint) {
		this.backHint = backHint;
	}

	public List<Long> getChoiceIds() {
		return choiceIds;
	}
	
	public void setChoiceIds(List<Long> choiceIds) {
		this.choiceIds = choiceIds;
	}

	public List<Card> getChildCards() {
		return childCards;
	}
	
	public void setChildCards(List<Card> childCards) {
		this.childCards = childCards;
	}
	
	public List<Choice> getChoices() {
		return choices;
	}
	
	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}
}
