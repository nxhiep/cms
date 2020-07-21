package com.hiepnx.cms.client.activities.home.widgets;

import org.gwtbootstrap3.client.ui.ListBox;
import org.gwtbootstrap3.client.ui.TextArea;
import org.gwtbootstrap3.client.ui.TextBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Widget;
import com.hiepnx.cms.client.view.BaseEditDialog;
import com.hiepnx.cms.client.view.Toaster;
import com.hiepnx.cms.shared.Config;
import com.hiepnx.cms.shared.model.Category;
import com.hiepnx.cms.shared.model.IBasic;

public class CreateCategoryDialog extends BaseEditDialog {

	private static CreateCategoryDialogUiBinder uiBinder = GWT.create(CreateCategoryDialogUiBinder.class);

	interface CreateCategoryDialogUiBinder extends UiBinder<Widget, CreateCategoryDialog> {
	}

	@UiField TextBox nameBox;
	@UiField ListBox statusListBox;
	@UiField TextArea descriptionBox;

	public CreateCategoryDialog() {
		setContent(uiBinder.createAndBindUi(this));
		statusListBox.addItem("Public", Config.STATUS_PUBLIC+"");
		statusListBox.addItem("Private", Config.STATUS_PRIVATE+"");
		statusListBox.addItem("Delete", Config.STATUS_DELETED+"");
		statusListBox.setSelectedIndex(0);
	}

	@Override
	protected void updateData(IBasic iBasic) {
		super.updateData(iBasic);
		if(iBasic == null) {
			nameBox.setValue("");
			descriptionBox.setValue("");
			statusListBox.setSelectedIndex(0);
			return;
		}
		Category category = (Category) iBasic;
		nameBox.setValue(category.getName());
		descriptionBox.setValue(category.getDescription());
		for (int index = 0; index < statusListBox.getItemCount(); index++) {
			if (statusListBox.getValue(index).equals(category.getStatus() + "")) {
				statusListBox.setSelectedIndex(index);
				break;
			}
		}
	}

	public Category getCaterory() {
		if(iBasic == null) {
			iBasic = new Category();
		}
		Category category = (Category) iBasic;
		category.setName(nameBox.getValue());
		category.setDescription(descriptionBox.getValue());
		category.setStatus(Integer.parseInt(statusListBox.getSelectedValue()));
		return category;
	}

	public boolean isValidated() {
		if(nameBox.getValue().isEmpty()) {
			Toaster.showError("Name empty!");
			return false;
		}
		return true;
	}
}
