package com.hiepnx.cms.client.activities.category.widgets;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.IconType;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.hiepnx.cms.shared.Callback;
import com.hiepnx.cms.shared.Config;
import com.hiepnx.cms.shared.model.Card;
import com.hiepnx.cms.shared.model.Choice;

public class ChoicesPanel extends FlowPanel {
	
	private List<ChoiceItem> createChoiceItems = new ArrayList<ChoiceItem>();
	private FlowPanel choicesContentPanel = new FlowPanel();
	private FlowPanel controlsPanel = new FlowPanel();
	private Card card;
	private Button buttonCreate = new Button("", IconType.PLUS, new ClickHandler() {
		
		@Override
		public void onClick(ClickEvent arg0) {
			addChoiceItem(null);
		}
	});
	
	public ChoicesPanel() {
		this.setStyleName("choices-panel");
		this.add(choicesContentPanel);
		this.add(controlsPanel);
		controlsPanel.add(buttonCreate);
		buttonCreate.setType(ButtonType.SUCCESS);
	}
	
	public void setCard(Card card) {
		this.card = card;
		if(card != null && card.getChoices() != null && !card.getChoices().isEmpty()) {
			for(Choice choice : card.getChoices()) {
				addChoiceItem(choice);
			}
		}
	}
	
	public ArrayList<Choice> getChoices() {
		ArrayList<Choice> choices = new ArrayList<Choice>();
		for(ChoiceItem choiceItem : createChoiceItems) {
			choices.add(choiceItem.getChoice());
			choiceItem.getChoice().setParentId(card != null ? card.getId() : Config.NULL_ID);
		}
		return choices;
	}
	
	private void addChoiceItem(Choice choice) {
		final ChoiceItem createChoiceItem = new ChoiceItem(choice);
		createChoiceItem.setRemoveHandler(new Callback<Void>() {
			
			@Override
			public void onCallback(Void object) {
				choicesContentPanel.remove(createChoiceItem);
				createChoiceItems.remove(createChoiceItem);
			}
		});
		createChoiceItem.setCorrectAnswer(createChoiceItems.isEmpty());
		choicesContentPanel.add(createChoiceItem);
		createChoiceItems.add(createChoiceItem);
	}
}
