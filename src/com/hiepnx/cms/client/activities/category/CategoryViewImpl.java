package com.hiepnx.cms.client.activities.category;

import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.TextBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.hiepnx.cms.client.activities.basic.BasicViewImpl;
import com.hiepnx.cms.client.activities.category.widgets.CardItem;
import com.hiepnx.cms.client.activities.category.widgets.CreateCardDialog;
import com.hiepnx.cms.client.event.PageEvent;
import com.hiepnx.cms.shared.model.Card;

public class CategoryViewImpl extends BasicViewImpl implements CategoryView {

	private static CategoryViewImplUiBinder uiBinder = GWT.create(CategoryViewImplUiBinder.class);

	interface CategoryViewImplUiBinder extends UiBinder<Widget, CategoryViewImpl> {
	}

	@UiField TextBox searchBox;
	@UiField FlowPanel contentPanel, pagePanel;
	@UiField Button buttonCreateCard;
	private CreateCardDialog createCardDialog;
	
	public CategoryViewImpl() {
		super();
		setView(uiBinder.createAndBindUi(this));
		searchBox.setWidth("300px");
	}

	@Override
	public void showPages(int totalCard, int limit) {
		pagePanel.clear();
		if(totalCard == 0) {
			pagePanel.add(new HTML("Empty!"));
		}
		int pages = totalCard/limit;
		for (int index = 0; index < pages; index++) {
			final int offset = index;
			Button button = new Button("" + (offset + 1));
			pagePanel.add(button);
			button.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent arg0) {
					getEventBus().fireEvent(new PageEvent(offset));
				}
			});
		}
	}
	
	@Override
	public void showCards(int offset, List<Card> cards) {
		contentPanel.clear();
		int index = offset;
		for (Card card : cards) {
			contentPanel.add(new CardItem(index++, card));
		}
	}
	
	@Override
	public HasClickHandlers getButtonCreateCard() {
		return buttonCreateCard;
	}
	
	@Override
	public CreateCardDialog getCreateCardDialog() {
		if(createCardDialog == null) {
			createCardDialog = new CreateCardDialog();
		}
		return createCardDialog;
	}
}
