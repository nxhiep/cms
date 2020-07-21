package com.hiepnx.cms.client.activities.basic.widgets;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.TextBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.hiepnx.cms.client.LoginManager;
import com.hiepnx.cms.client.view.AvatarPanel;

public class HeaderPanel extends Composite {

	private static HeaderPanelUiBinder uiBinder = GWT.create(HeaderPanelUiBinder.class);

	interface HeaderPanelUiBinder extends UiBinder<Widget, HeaderPanel> {
	}
	
	@UiField Button buttonLogin, buttonRegister;
	@UiField FlowPanel loginPanel;
	@UiField HTMLPanel userPanel;
	@UiField HTML userName;
	@UiField Anchor buttonLogout;
	@UiField AvatarPanel avatarPanel;
	@UiField TextBox headerSearchBox;

	public HeaderPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		loginPanel.setVisible(!LoginManager.isLogedIn());
		userPanel.setVisible(LoginManager.isLogedIn());
		if(LoginManager.isLogedIn()) {
			avatarPanel.initAvatar(LoginManager.getCurrentUserId());
			userName.setHTML(LoginManager.getCurrentUser().getDisplayName());
		}
		headerSearchBox.setPlaceholder("Tìm kiếm...");
	}

	public Button getButtonLogin() {
		return buttonLogin;
	}
	
	public Button getButtonRegister() {
		return buttonRegister;
	}
	
	public Anchor getButtonLogout() {
		return buttonLogout;
	}
}
