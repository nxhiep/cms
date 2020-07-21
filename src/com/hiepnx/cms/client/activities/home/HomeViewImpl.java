package com.hiepnx.cms.client.activities.home;

import java.util.ArrayList;
import java.util.List;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.IconType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.hiepnx.cms.client.activities.basic.BasicViewImpl;
import com.hiepnx.cms.client.activities.home.widgets.CreateCategoryDialog;
import com.hiepnx.cms.client.view.tree.TreeOptions;
import com.hiepnx.cms.client.view.tree.TreePanel;
import com.hiepnx.cms.shared.model.Category;
import com.hiepnx.cms.shared.model.IBasic;

public class HomeViewImpl extends BasicViewImpl implements HomeView {

	private static BasicViewImplUiBinder uiBinder = GWT.create(BasicViewImplUiBinder.class);

	interface BasicViewImplUiBinder extends UiBinder<Widget, HomeViewImpl> {
	}
	
	@UiField TextBox searchBox;
	@UiField FlowPanel contentPanel;
	private TreePanel treePanel = new TreePanel(new TreeOptions(true));
	private CreateCategoryDialog createCategoryDialog;
	private Button buttonCreateCategory = new Button("Create Category");
	
	public HomeViewImpl() {
		super();
		setView(uiBinder.createAndBindUi(this));
		contentPanel.clear();
		contentPanel.add(buttonCreateCategory);
		contentPanel.add(treePanel);
		buttonCreateCategory.setIcon(IconType.PLUS);
		buttonCreateCategory.setType(ButtonType.PRIMARY);
	}
	
	@Override
	public void refreshView() {
		super.refreshView();
	}

	@Override
	public void show(List<Category> categories) {
		if(categories == null || categories.isEmpty()) {
			treePanel.clear();
			treePanel.add(new HTML("Trống!"));
			return;
		}
		treePanel.updateData(new ArrayList<IBasic>(categories));
	}
	
	@Override
	public CreateCategoryDialog getCreateCategoryDialog() {
		if(createCategoryDialog == null) {
			createCategoryDialog = new CreateCategoryDialog();
		}
		return createCategoryDialog;
	}
	
	@Override
	public Button getButtonCreateCategory() {
		return buttonCreateCategory;
	}
}
