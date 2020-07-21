package com.hiepnx.cms.client.view.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.constants.Toggle;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.hiepnx.cms.client.CMS;
import com.hiepnx.cms.client.event.ActionEvent;
import com.hiepnx.cms.client.event.ActionEvent.Action;
import com.hiepnx.cms.client.view.MyDropdown;
import com.hiepnx.cms.shared.Callback;
import com.hiepnx.cms.shared.model.Category;
import com.hiepnx.cms.shared.model.IBasic;

public class TreeItem extends FlowPanel {
	
	private TreeOptions options;
	private IBasic iBasic;
	private FlowPanel titlePanel = new FlowPanel();
	private FlowPanel childsPanel = new FlowPanel();
	private HTML nameHTML = new HTML();
	private Button buttonExpand = new Button();
	private MyDropdown dropdownMenu = new MyDropdown(IconType.BARS, 
			new ArrayList<String>(Arrays.asList("Create", "Edit", "Delete")), new Callback<Integer>() {
		
		@Override
		public void onCallback(Integer object) {
			Action action = Action.VIEW;
			if(object == 0) {
				action = Action.ADD;
			} else if(object == 1) {
				action = Action.EDIT;
			} else if(object == 2) {
				action = Action.DELETE;
			}
			CMS.CLIENT_FACTORY.getEventBus().fireEvent(new ActionEvent(iBasic, action));
		}
	});
	
	public TreeItem(IBasic iBasic, TreeOptions options) {
		this.iBasic = iBasic;
		this.options = options;
		this.add(titlePanel);
		this.add(childsPanel);
		buttonExpand.setIcon(IconType.CARET_DOWN);
		titlePanel.add(buttonExpand);
		titlePanel.add(nameHTML);
		titlePanel.add(dropdownMenu);
		this.getElement().getStyle().setProperty("borderBottom", "1px solid #ddd");
		titlePanel.setStyleName("flex flex-ai-center");
		dropdownMenu.getElement().getStyle().setProperty("marginLeft", "auto");
		String keyCollpase = "key-collapse-tree-item-" + iBasic.getId();
		buttonExpand.setDataToggle(Toggle.COLLAPSE);
		buttonExpand.setDataTarget("#"+keyCollpase);
		childsPanel.setStyleName("collapse");
		childsPanel.getElement().setId(keyCollpase);
		showView();
		bind();
	}

	private void bind() {
		buttonExpand.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				
			}
		});
	}

	private void showView() {
		if(iBasic instanceof Category) {
			Category category = (Category) iBasic;
			nameHTML.setHTML(category.getName());
			buttonExpand.setVisible(!category.getChildCategories().isEmpty());
			if(!category.getChildCategories().isEmpty()) {
				showChild(new ArrayList<IBasic>(category.getChildCategories()));
			}
		}
	}
	
	public void showChild(List<IBasic> iBasics) {
		childsPanel.clear();
		for (IBasic basic : iBasics) {
			childsPanel.add(new TreeItem(basic, options));
		}
	}
}
