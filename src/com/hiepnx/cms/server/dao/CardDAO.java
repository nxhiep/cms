package com.hiepnx.cms.server.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.googlecode.objectify.cmd.Query;
import com.hiepnx.cms.shared.Config;
import com.hiepnx.cms.shared.model.Card;
import com.hiepnx.cms.shared.model.Choice;
import com.hiepnx.cms.shared.model.IBasic;

public class CardDAO extends DAO {
	
	public List<Card> getCardsByIds(List<Long> cardIds) {
		if(cardIds == null || cardIds.isEmpty()) {
			return new ArrayList<Card>();
		}
		Map<Long, Card> mapCard = ofy().load().type(Card.class).ids(cardIds);
		for (Card card : mapCard.values()) {
			card.setChildCards(getCardsByParentId(card.getId()));
		}
		Map<Long, ArrayList<Choice>> mapChoices = getMapChoicesByCardIds(cardIds);
		for (Card card : mapCard.values()) {
			if(mapChoices.containsKey(card.getId())) {
				card.setChoices(mapChoices.get(card.getId()));
			}
		}
		return new ArrayList<Card>(mapCard.values());
	}
	
	public List<Card> getCardsByParentId(Long parentId) {
		List<Card> cards = new ArrayList<Card>(ofy().load().type(Card.class).filter("parentId", parentId).list());
		List<Long> cardIds = new ArrayList<Long>();
		for (Card card : cards) {
			card.setChildCards(getCardsByParentId(card.getId()));
			cardIds.add(card.getId());
		}
		Map<Long, ArrayList<Choice>> mapChoices = getMapChoicesByCardIds(cardIds);
		for (Card card : cards) {
			if(mapChoices.containsKey(card.getId())) {
				card.setChoices(mapChoices.get(card.getId()));
			}
		}
		return cards;
	}
	
	public int countCardsByParentId(Long parentId) {
		return ofy().load().type(Card.class)
			.filter("parentId", parentId).filter("status", Config.STATUS_PUBLIC).count();
	}
	
	public List<Card> getCardsByParentId(Long parentId, int offset, int limit) {
		Query<Card> query = ofy().load().type(Card.class)
			.filter("parentId", parentId).filter("status", Config.STATUS_PUBLIC).order("-lastUpdate");
		if(offset >= 0) {
			query = query.offset(offset);
		}
		if(limit > 0) {
			query = query.limit(limit);
		}
		List<Card> cards = new ArrayList<Card>(query.list());
		List<Long> cardIds = new ArrayList<Long>();
		for (Card card : cards) {
			card.setChildCards(getCardsByParentId(card.getId()));
			cardIds.add(card.getId());
		}
		Map<Long, ArrayList<Choice>> mapChoices = getMapChoicesByCardIds(cardIds);
		for (Card card : cards) {
			if(mapChoices.containsKey(card.getId())) {
				card.setChoices(mapChoices.get(card.getId()));
			}
		}
		return cards;
	}
	
	private Map<Long, ArrayList<Choice>> getMapChoicesByCardIds(List<Long> cardIds) {
		Map<Long, ArrayList<Choice>> mapChoices = new HashMap<Long, ArrayList<Choice>>();
		if(cardIds != null && !cardIds.isEmpty()) {
			List<Choice> choices = new ArrayList<Choice>(ofy().load().type(Choice.class).filter("parentId in", cardIds).list());
			for (Choice choice : choices) {
				if(!mapChoices.containsKey(choice.getParentId())) {
					mapChoices.put(choice.getParentId(), new ArrayList<Choice>());
				}
				mapChoices.get(choice.getParentId()).add(choice);
			}
		}
		return mapChoices;
	}

	public void deleteCard(Card card) {
		List<Choice> choices = card.getChoices();
		if(choices != null && !choices.isEmpty()) {
			deletes(new ArrayList<IBasic>(choices));
		}
		delete(card);
	}

	public Card saveCard(Card card) {
		ofy().save().entity(card).now();
		ArrayList<Choice> choices = card.getChoices();
		long currentTime = new Date().getTime();
		if(choices != null && !choices.isEmpty()) {
			for (Choice choice : choices) {
				choice.setParentId(card.getId());
				if(choice.getId() == null || choice.getId() == Config.NULL_ID) {
					choice.setCreateDate(currentTime);
				}
				choice.setLastUpdate(currentTime);
			}
			ofy().save().entities(choices).now();
			card.setChoices(choices);
		}
		if(card.getId() == null || card.getId() == Config.NULL_ID) {
			card.setCreateDate(currentTime);
		}
		card.setLastUpdate(currentTime);
		ofy().save().entity(card).now();
		return card;
	}

	public List<Card> getAllCards() {
		return new ArrayList<Card>(ofy().load().type(Card.class).filter("status", Config.STATUS_PUBLIC).list());
	}

	public List<Choice> getAllChoices() {
		return new ArrayList<Choice>(ofy().load().type(Choice.class).filter("status", Config.STATUS_PUBLIC).list());
	}
}
