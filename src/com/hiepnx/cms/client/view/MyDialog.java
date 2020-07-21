/**
 * 
 */
package com.hiepnx.cms.client.view;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Modal;
import org.gwtbootstrap3.client.ui.ModalBody;
import org.gwtbootstrap3.client.ui.ModalFooter;
import org.gwtbootstrap3.client.ui.ModalHeader;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.ModalBackdrop;

import com.google.gwt.dom.client.Style.Overflow;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.hiepnx.cms.client.ClientUtils;
import com.hiepnx.cms.shared.Callback;

public class MyDialog extends Modal {

	protected FlowPanel contentPanel = new FlowPanel();
	protected Button okButton = new Button("OK");
	protected Button cancelButton = new Button("Cancel");
	protected HTML headerHTML = new HTML("");
	protected Callback<Boolean> confirmCallBack = null;
	protected ModalHeader header = new ModalHeader();
	protected ModalBody body = new ModalBody();
	protected ModalFooter footer = new ModalFooter();
	
	public MyDialog() {
		super();
		this.getElement().getStyle().setProperty("clip", "auto !important");
		body.setWidth("100%");
		contentPanel.setWidth("100%");
		this.setClosable(true);
		this.setFade(true);
		okButton.setType(ButtonType.PRIMARY);
		cancelButton.setType(ButtonType.DANGER);
		this.add(header);
		this.add(body);
		this.add(footer);
		body.add(contentPanel);
		footer.add(okButton);
		footer.add(cancelButton);
		header.add(headerHTML);
		this.setDataBackdrop(ModalBackdrop.STATIC);
		okButton.setText("OK");
	    okButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(confirmCallBack != null) {
					confirmCallBack.onCallback(true);
				}
				if (confirmCallBack == null) {
					hide();
				}
			}
		});
	    cancelButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if(confirmCallBack != null) {
					confirmCallBack.onCallback(false);
				}
				if(confirmCallBack == null) {
					hide();
				}
			}
		});
	}
	
	public MyDialog(String width) {
		this();
		this.setWidth(ClientUtils.isMobileWeb() ? "100%" : (width != null && !width.isEmpty() ? width : "80%"));
	}
	
	public void show(String title, Widget content, String okButton, String cancelButton, Callback<Boolean> callBack) {
		this.confirmCallBack = callBack;
		this.show(title, content, okButton, cancelButton);
	}
	
	public void showQuestion(String title, Callback<Boolean> callBack) {
		this.confirmCallBack = callBack;
		this.show(title, null, "Ok", "Cancel");
	}
	
	protected void setContent(Widget widget) {
		contentPanel.clear();
		contentPanel.add(widget);
	}
	
	public void showWithCenter(String title, Widget content, String okButton, String cancelButton, boolean center) {
		headerHTML.setHTML("<b>" + title + "</b>");
		if (content != null) {
			setContent(content);
		}
		if (okButton != null && !okButton.isEmpty()) {
			this.okButton.setVisible(true);
			this.okButton.setText(okButton);
		}
		else {
			this.okButton.setVisible(false);
		}
		if (cancelButton != null && !cancelButton.isEmpty()) {
			this.cancelButton.setVisible(true);
			this.cancelButton.setText(cancelButton);
		}
		else {
			this.cancelButton.setVisible(false);
		}
		this.show();
	}
	
	
	public void show(String title, Widget content, String okButton, String cancelButton) {
		showWithCenter(title, content, okButton, cancelButton, true);
	}
	
	public FlowPanel getMainPanel() {
		return this.contentPanel;
	}
	
	public Button getOkButton() {
		return this.okButton;
	}
	
	public Button getCancelButton() {
		return this.cancelButton;
	}
	
	public ModalBody getModalBody() {
		return this.body;
	}
	
	public void setFixedHeight(int height) {
		this.getModalBody().getElement().getStyle().setOverflow(Overflow.AUTO);
		this.getModalBody().setHeight(height + "px");
	}
}