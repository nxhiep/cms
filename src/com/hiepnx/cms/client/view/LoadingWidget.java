package com.hiepnx.cms.client.view;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

public class LoadingWidget extends FlowPanel {
	protected FlowPanel loadingPanel = new FlowPanel();
	protected HTML loadingContentWidget = new HTML();
	protected HTML loadingTitle = new HTML();
	
	public LoadingWidget() {
		this.add(loadingTitle);
		this.add(loadingPanel);
		loadingTitle.setSize("30px", "30px");
		loadingPanel.setSize("100px", "10px");
		loadingContentWidget.setSize("0px", "10px");
		loadingPanel.add(loadingContentWidget);
		this.setWidth("130px");
		this.setStyleName("loading-widget flex flex-ai-center");
		loadingTitle.setStyleName("loading-title");
		loadingPanel.setStyleName("loading-panel");
		loadingContentWidget.setStyleName("loading-content-widget");
	}
	
	public void setPercent(float percent) {
		loadingContentWidget.setWidth(percent + "%");
		loadingContentWidget.getElement().getStyle().setBackgroundColor("green");
		if(percent < 100) {
			loadingTitle.setHTML(percent + "%");
		} else {
			loadingTitle.setHTML("<i style=\"color:green\" class=\"fa fa-check\"></i>");
		}
	}
	
	public void setFailed() {
		loadingTitle.setHTML("<i style=\"color:red\" class=\"fa fa-times\"></i>");
	}
}
