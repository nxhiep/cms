package com.hiepnx.cms.client.activities.home;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.hiepnx.cms.client.ClientData;
import com.hiepnx.cms.client.activities.ClientFactory;
import com.hiepnx.cms.client.activities.basic.BasicActivity;
import com.hiepnx.cms.client.activities.home.widgets.CreateCategoryDialog;
import com.hiepnx.cms.client.event.ActionEvent;
import com.hiepnx.cms.client.event.ActionEvent.Action;
import com.hiepnx.cms.client.event.ActionEventHandler;
import com.hiepnx.cms.client.view.MyDialog;
import com.hiepnx.cms.client.view.Toaster;
import com.hiepnx.cms.shared.Callback;
import com.hiepnx.cms.shared.Config;
import com.hiepnx.cms.shared.model.Category;
import com.hiepnx.cms.shared.model.IBasic;

public class HomeActivity extends BasicActivity {
	
	private HomeView view;
	
	public HomeActivity(Place place, ClientFactory clientFactory) {
		super(place, clientFactory);
	}
	
	@Override
	protected void getParams(Place place) {
		super.getParams(place);
	}
	
	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view = clientFactory.getHomeView();
		super.start(panel, eventBus, view);
	}
	
	@Override
	protected void bind() {
		super.bind();
		addHandlerRegistration(view.getButtonCreateCategory().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				createAndUpdateCategory(null, Config.NULL_ID);
			}
		}));
		addHandlerRegistration(eventBus.addHandler(ActionEvent.TYPE, new ActionEventHandler() {
			
			@Override
			public void onEvent(ActionEvent event) {
				if(event.getIBasic() instanceof Category) {
					Category category = (Category) event.getIBasic();
					if(Action.ADD.equals(event.getAction())) {
						createAndUpdateCategory(null, category.getId());
					} else if(Action.EDIT.equals(event.getAction())) {
						createAndUpdateCategory(category, category.getId());
					} else if(Action.DELETE.equals(event.getAction())) {
						deleteCategory(category);
					}
				}
			}
		}));
	}
	
	@Override
	protected void loadData() {
		super.loadData();
		loadCategories();
	}
	
	protected void createAndUpdateCategory(Category category, Long parentId) {
		CreateCategoryDialog createCategoryDialog = view.getCreateCategoryDialog();
		createCategoryDialog.show(category == null ? "Create Category" : "Edit Category", category, new Callback<Boolean>() {
			
			@Override
			public void onCallback(Boolean object) {
				if(object) {
					if(createCategoryDialog.isValidated()) {
						Category category = createCategoryDialog.getCaterory();
						category.setParentId(parentId);
						updateCategory(category);
					}
				}
				createCategoryDialog.hide();
			}

		});
	}
	
	protected void deleteCategory(Category category) {
		MyDialog myDialog = new MyDialog();
		myDialog.showQuestion("Delete " + category.getName() + "?", new Callback<Boolean>() {
			
			@Override
			public void onCallback(Boolean object) {
				if(object) {
					ClientData.DATA_SERVICE.delete(category, new AsyncCallback<Void>() {
						
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
	
	private void loadCategories() {
		ClientData.DATA_SERVICE.getCategoriesByParentId(Config.NULL_ID, new AsyncCallback<List<Category>>() {

			@Override
			public void onSuccess(List<Category> arg0) {
				Collections.sort(arg0, new Comparator<Category>() {

					@Override
					public int compare(Category o1, Category o2) {
						return Long.compare(o2.getLastUpdate(), o1.getLastUpdate());
					}
				});
				view.show(arg0);
			}
			
			@Override
			public void onFailure(Throwable arg0) {
				view.show(null);
			}
		});
	}

	private void updateCategory(Category category) {
		ClientData.DATA_SERVICE.save(category, new AsyncCallback<IBasic>() {
			
			@Override
			public void onSuccess(IBasic arg0) {
				Toaster.showSuccess("Updated!");
				loadCategories();
			}
			
			@Override
			public void onFailure(Throwable arg0) {
			}
		});
	}
}
