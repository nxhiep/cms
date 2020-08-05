package com.hiepnx.cms.client.activities.category.widgets;

import java.util.ArrayList;
import java.util.Arrays;

import org.gwtbootstrap3.client.ui.constants.IconType;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.hiepnx.cms.client.CMS;
import com.hiepnx.cms.client.event.ActionEvent;
import com.hiepnx.cms.client.event.ActionEvent.Action;
import com.hiepnx.cms.client.view.MyDropdown;
import com.hiepnx.cms.shared.Callback;
import com.hiepnx.cms.shared.model.Card;
import com.hiepnx.cms.shared.model.Choice;

public class CardItem extends FlowPanel {

	private Card card;
	private MyDropdown dropdownMenu = new MyDropdown(IconType.BARS, 
			new ArrayList<String>(Arrays.asList("Edit", "Delete")), new Callback<Integer>() {
		
		@Override
		public void onCallback(Integer object) {
			Action action = Action.VIEW;
			if(object == 0) {
				action = Action.EDIT;
			} else if(object == 1) {
				action = Action.DELETE;
			}
			CMS.CLIENT_FACTORY.getEventBus().fireEvent(new ActionEvent(card, action));
		}
	});
	
	public CardItem(int index, Card card) {
		this.card = card;
		this.setStyleName("card-item flex");
		HTML indexHTML = new HTML((index + 1) + "");
		indexHTML.setStyleName("card-index");
		this.add(indexHTML);
		HTML contentHTML = new HTML(card.getFrontText());
		contentHTML.setStyleName("card-content");
		this.add(contentHTML);
		FlowPanel childPanel = new FlowPanel();
		childPanel.setStyleName("card-child-panel");
		if(!card.getChoices().isEmpty()) {
			for (Choice choice : card.getChoices()) {
				String label = (choice.isCorrect() ? "<strong style=\"color:red\">*</strong>" : "");
				HTML choiceContentHTML = new HTML(label + "<span>" + choice.getFrontText() + "</span>");
				choiceContentHTML.setStyleName("card-choice-content");
				childPanel.add(choiceContentHTML);
			}
		}
		this.add(childPanel);
		HTML hintHTML = new HTML("<strong>Giải thích: </strong>" + "<span>" + card.getBackHint() + "</span>");
		hintHTML.setStyleName("card-hint");
		this.add(hintHTML);
		dropdownMenu.addStyleName("card-dropdown-menu");
		this.add(dropdownMenu);
	}

	public Card getCard() {
		return card;
	}
}
