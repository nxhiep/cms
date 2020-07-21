package com.hiepnx.cms.server.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.hiepnx.cms.shared.model.Card;

public class CardDAO extends DAO {
	
	public List<Card> getCardsByParentId(Long parentId) {
		List<Card> cards = new ArrayList<Card>(ofy().load().type(Card.class).filter("parentId", parentId).list());
		for (Card card : cards) {
			card.setChildCards(getCardsByParentId(card.getId()));
		}
		return cards;
	}
}
