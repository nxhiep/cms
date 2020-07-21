package com.hiepnx.cms.client.activities.basic;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.hiepnx.cms.client.activities.basic.widgets.HeaderPanel;

public class BasicViewImpl extends Composite implements BasicView {

	private static BasicViewImplUiBinder uiBinder = GWT.create(BasicViewImplUiBinder.class);

	interface BasicViewImplUiBinder extends UiBinder<Widget, BasicLayout> {
	}
	
	protected EventBus eventBus;
	private final BasicLayout basicLayout;
	
	public static class BasicLayout {
		@UiField protected HTMLPanel mainPanel;
		@UiField protected HeaderPanel headerPanel;
		@UiField protected FlowPanel contentPanel;
		private BasicViewImpl basicViewImpl;
		
		public BasicLayout(BasicViewImpl basicViewImpl) {
			this.basicViewImpl = basicViewImpl;
		}
		
		public HTMLPanel getMainPanel() {
			return mainPanel;
		}
		
		public Widget asWidget() {
			return basicViewImpl;
		}
		
		public HeaderPanel getHeaderPanel() {
			return headerPanel;
		}
		
		public FlowPanel getContentPanel() {
			return contentPanel;
		}
	}
	
	public BasicViewImpl() {
		this.basicLayout = new BasicLayout(this);
		initWidget(uiBinder.createAndBindUi(this.basicLayout));
	}
	
	protected void setView(Widget widget) {
		getBasicLayout().getContentPanel().clear();
		getBasicLayout().getContentPanel().add(widget);
	}
	
	@Override
	public BasicLayout getBasicLayout() {
		return basicLayout;
	}
	
	@Override
	public void refreshView() {
	}

	@Override
	public void setEventBus(EventBus eventBus) {
		this.eventBus = eventBus;
	}
	
	@Override
	public HeaderPanel getHeaderPanel() {
		return basicLayout.getHeaderPanel();
	}
}
