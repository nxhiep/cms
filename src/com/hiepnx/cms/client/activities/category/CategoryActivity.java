package com.hiepnx.cms.client.activities.category;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.hiepnx.cms.client.ClientData;
import com.hiepnx.cms.client.ClientUtils;
import com.hiepnx.cms.client.RPCCall;
import com.hiepnx.cms.client.activities.ClientFactory;
import com.hiepnx.cms.client.activities.basic.BasicActivity;
import com.hiepnx.cms.client.activities.category.widgets.CreateCardDialog;
import com.hiepnx.cms.client.event.ActionEvent;
import com.hiepnx.cms.client.event.ActionEvent.Action;
import com.hiepnx.cms.client.event.ActionEventHandler;
import com.hiepnx.cms.client.event.PageEvent;
import com.hiepnx.cms.client.event.PageEventHandler;
import com.hiepnx.cms.client.view.MyDialog;
import com.hiepnx.cms.shared.Callback;
import com.hiepnx.cms.shared.model.Card;
import com.hiepnx.cms.shared.model.Category;

public class CategoryActivity extends BasicActivity {
	
	private CategoryView view;
	private Long categoryId;
	private Category category;
	private final int LIMIT = 50;
	private int currentOffset = 0;
	private Map<Integer, List<Card>> mapCardOffset = new HashMap<Integer, List<Card>>();
	
	public CategoryActivity(Place place, ClientFactory clientFactory) {
		super(place, clientFactory);
	}
	
	@Override
	protected void getParams(Place place) {
		super.getParams(place);
		categoryId = ((CategoryPlace) place).getId();
		category = ((CategoryPlace) place).getCategory();
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view = clientFactory.getCategoryView();
		super.start(panel, eventBus, view);
	}
	
	@Override
	protected void bind() {
		super.bind();
		addHandlerRegistration(view.getButtonCreateCard().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				createAndUpdateCard(null);
			}
		}));
		addHandlerRegistration(eventBus.addHandler(ActionEvent.TYPE, new ActionEventHandler() {
			
			@Override
			public void onEvent(ActionEvent event) {
				if(event.getIBasic() instanceof Card) {
					Card card = (Card) event.getIBasic();
					if(Action.ADD.equals(event.getAction())) {
						createAndUpdateCard(null);
					} else if(Action.EDIT.equals(event.getAction())) {
						createAndUpdateCard(card);
					} else if(Action.DELETE.equals(event.getAction())) {
						deleteCard(card);
					}
				}
			}
		}));
		addHandlerRegistration(eventBus.addHandler(PageEvent.TYPE, new PageEventHandler() {
			
			@Override
			public void onEvent(PageEvent event) {
				loadCards(event.getOffset(), false);
			}
		}));
	}
	
	@Override
	protected void loadData() {
		super.loadData();
		if(category == null) {
			new RPCCall<Category>() {

				@Override
				public void onFailure(Throwable arg0) {
				}

				@Override
				public void onSuccess(Category arg0) {
					CategoryActivity.this.category = arg0;
					CategoryActivity.this.categoryId = arg0.getId();
					loadedCategory();
				}

				@Override
				protected void callService(AsyncCallback<Category> cb) {
					ClientData.DATA_SERVICE.getCategoryById(categoryId, cb);
				}
			}.retry(0, true);
		} else {
			loadedCategory();
		}
	}

	protected void loadedCategory() {
		new RPCCall<Integer>() {

			@Override
			public void onFailure(Throwable arg0) {
			}

			@Override
			public void onSuccess(Integer totalCard) {
				view.showPages(totalCard, LIMIT);
				loadCards(0, true);
			}

			@Override
			protected void callService(AsyncCallback<Integer> callback) {
				ClientData.DATA_SERVICE.countCardsByParentId(categoryId, callback);
			}
		}.retry(0, true);
	}
	
	private void loadCards(int offset, boolean clean) {
		this.currentOffset = offset;
		if(!clean && mapCardOffset.containsKey(offset) && !mapCardOffset.get(offset).isEmpty()) {
			view.showCards(offset, mapCardOffset.get(offset));
			return;
		}
		new RPCCall<List<Card>>() {

			@Override
			public void onFailure(Throwable arg0) {
			}

			@Override
			public void onSuccess(List<Card> cards) {
				ClientUtils.log("cards " + cards.size());
				mapCardOffset.put(offset, cards);
				view.showCards(offset, cards);
			}

			@Override
			protected void callService(AsyncCallback<List<Card>> cb) {
				ClientData.DATA_SERVICE.getCardsByParentId(categoryId, offset, LIMIT, cb);
			}
		}.retry(0, true);
	}
	
	protected void createAndUpdateCard(Card card) {
		CreateCardDialog createCardDialog = view.getCreateCardDialog();
		createCardDialog.show(card == null ? "Create Card" : "Edit Card", card, new Callback<Boolean>() {
			
			@Override
			public void onCallback(Boolean object) {
				if(object) {
					if(createCardDialog.isValidated()) {
						Card card = createCardDialog.getCard();
						updateCard(card);
					}
				}
				createCardDialog.hide();
			}

		});
	}
	
	private void updateCard(Card card) {
		card.setParentId(categoryId);
		ClientData.DATA_SERVICE.saveCard(card, new AsyncCallback<Card>() {
			
			@Override
			public void onSuccess(Card arg0) {
				loadCards(currentOffset, true);
			}
			
			@Override
			public void onFailure(Throwable arg0) {
			}
		});
	}
	
	private void deleteCard(Card card) {
		MyDialog myDialog = new MyDialog();
		myDialog.showQuestion("Delete " + card.getId() + "?", new Callback<Boolean>() {
			
			@Override
			public void onCallback(Boolean object) {
				if(object) {
					ClientData.DATA_SERVICE.deleteCard(card, new AsyncCallback<Void>() {
						
						@Override
						public void onSuccess(Void arg0) {}
						
						@Override
						public void onFailure(Throwable arg0) {}
					});
				}
				myDialog.hide();
			}
		});
	}
}
