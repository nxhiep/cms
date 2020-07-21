package com.hiepnx.cms.client.activities.home;

import java.util.List;

import org.gwtbootstrap3.client.ui.Button;

import com.hiepnx.cms.client.activities.basic.BasicView;
import com.hiepnx.cms.client.activities.home.widgets.CreateCategoryDialog;
import com.hiepnx.cms.shared.model.Category;

public interface HomeView extends BasicView {

	void show(List<Category> cards);

	CreateCategoryDialog getCreateCategoryDialog();

	Button getButtonCreateCategory();
}
