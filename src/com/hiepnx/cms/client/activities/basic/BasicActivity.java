package com.hiepnx.cms.client.activities.basic;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.activity.shared.AbstractActivity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.hiepnx.cms.client.ClientData;
import com.hiepnx.cms.client.LoginManager;
import com.hiepnx.cms.client.activities.ClientFactory;
import com.hiepnx.cms.client.view.Toaster;

public class BasicActivity extends AbstractActivity {

	protected ClientFactory clientFactory;
	protected Place place;
	protected EventBus eventBus;
	protected BasicView view;
	protected List<HandlerRegistration> handlerRegistrations = new ArrayList<HandlerRegistration>();
	protected List<com.google.gwt.event.shared.HandlerRegistration> oldHandlers = new ArrayList<com.google.gwt.event.shared.HandlerRegistration>();
	
	public BasicActivity(Place place, ClientFactory clientFactory) {
		this.place = place;
		this.clientFactory = clientFactory;
		getParams(place);
	}

	protected void getParams(Place place) {
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		this.eventBus = eventBus;
	}

	public void start(AcceptsOneWidget panel, EventBus eventBus, BasicView view) {
		this.eventBus = eventBus;
		this.view = view;
		view.refreshView();
		view.setEventBus(eventBus);
		bind();
		panel.setWidget(view);
		loadData();
	}

	protected void bind() {
		removeHandlerRegistration();
		addHandlerRegistration(view.getHeaderPanel().getButtonLogin().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				LoginManager.openLoginDialog();
			}
		}));
		addHandlerRegistration(view.getHeaderPanel().getButtonRegister().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				LoginManager.openRegisterDialog();
			}
		}));
		addHandlerRegistration(view.getHeaderPanel().getButtonLogout().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				ClientData.DATA_SERVICE.logout(new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						Toaster.showError("Có lỗi xảy ra!");
					}

					@Override
					public void onSuccess(Void result) {
						Window.Location.reload();
					}
				});
			}
		}));
	}

	protected void loadData() {
	}

	public void goTo(Place place) {
		clientFactory.getPlaceController().goTo(place);
	}
	
	protected void addHandlerRegistration(HandlerRegistration registration) {
		handlerRegistrations.add(registration);
	}
	
	protected void addHandlerRegistration(com.google.gwt.event.shared.HandlerRegistration handlerRegistration) {
	    oldHandlers.add(handlerRegistration);
	}
	
	protected void removeHandlerRegistration() {
		for(HandlerRegistration registration : handlerRegistrations) {
			registration.removeHandler();
		}
		handlerRegistrations.clear();
		for(com.google.gwt.event.shared.HandlerRegistration registration : oldHandlers) {
			registration.removeHandler();
		}
		oldHandlers.clear();
	}
}
