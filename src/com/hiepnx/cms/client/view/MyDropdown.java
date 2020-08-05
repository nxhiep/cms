package com.hiepnx.cms.client.view;

import java.util.List;

import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.constants.Toggle;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.hiepnx.cms.shared.Callback;

public class MyDropdown extends FlowPanel {
	/*
	<div class="dropdown">
	  <button class="btn btn-primary dropdown-toggle" type="button" data-toggle="dropdown">Dropdown Example
	  <span class="caret"></span></button>
	  <ul class="dropdown-menu">
	    <li><a href="#">HTML</a></li>
	    <li><a href="#">CSS</a></li>
	    <li><a href="#">JavaScript</a></li>
	  </ul>
	</div>
	*/
	private Button button = new Button();
	private Callback<Integer> callback;

	public MyDropdown() {
		this.setStyleName("dropdown");
		button.setStyleName("button-menu-dropdown dropdown-toggle");
		button.getElement().setAttribute("data-toggle", Toggle.DROPDOWN.getToggle());
		this.add(button);
	}
	
	public MyDropdown(IconType iconType, List<String> childs, Callback<Integer> callback) {
		this();
		this.callback = callback;
		button.setHTML("<i class=\"fa "+iconType.getCssName()+"\"></i>");
		setChilds(childs);
	}
	
	private void setChilds(List<String> childs) {
		FlowPanel flowPanel = new FlowPanel();
		flowPanel.setStyleName("dropdown-menu dropdown-menu-right");
		for (String item : childs) {
			FlowPanel element = new FlowPanel();
			flowPanel.add(element);
			HTML anchor = new HTML(item);
			anchor.setStyleName("div-anchor");
			element.add(anchor);
			anchor.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent arg0) {
					callback.onCallback(childs.indexOf(item));
				}
			});
		}
		this.add(flowPanel);
	}
}
