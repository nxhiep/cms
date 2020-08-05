package com.hiepnx.cms.client.activities.category;

import java.util.List;

import com.google.gwt.event.dom.client.HasClickHandlers;
import com.hiepnx.cms.client.activities.basic.BasicView;
import com.hiepnx.cms.client.activities.category.widgets.CreateCardDialog;
import com.hiepnx.cms.shared.model.Card;

public interface CategoryView extends BasicView {

	void showPages(int totalCard, int limit);
	void showCards(int offset, List<Card> list);
	HasClickHandlers getButtonCreateCard();
	CreateCardDialog getCreateCardDialog();

}
