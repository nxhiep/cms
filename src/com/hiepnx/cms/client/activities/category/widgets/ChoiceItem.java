package com.hiepnx.cms.client.activities.category.widgets;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.CheckBox;
import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.constants.ButtonType;
import org.gwtbootstrap3.client.ui.constants.IconType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.hiepnx.cms.client.view.MyEditor;
import com.hiepnx.cms.client.view.UploaderPanel;
import com.hiepnx.cms.shared.Callback;
import com.hiepnx.cms.shared.Config;
import com.hiepnx.cms.shared.model.Choice;

public class ChoiceItem extends Composite {

	private static ChoiceItemUiBinder uiBinder = GWT.create(ChoiceItemUiBinder.class);

	interface ChoiceItemUiBinder extends UiBinder<Widget, ChoiceItem> {
	}

	@UiField MyEditor frontText, frontHint;
	@UiField UploaderPanel frontImageUploader;
	@UiField FlowPanel imagesPanel;
	@UiField CheckBox correctAnswerCheckBox;
	@UiField Button buttonClose;
	
	private String frontImage = Config.TEXT_EMPTY;
	private Choice choice;
	
	public ChoiceItem(Choice choice) {
		initWidget(uiBinder.createAndBindUi(this));
		this.choice = choice;
		this.setStyleName("choice-item");
		buttonClose.getElement().getStyle().setProperty("marginLeft", "auto");
		buttonClose.setIcon(IconType.CLOSE);
		buttonClose.setType(ButtonType.DANGER);
		imagesPanel.getElement().getStyle().setPaddingLeft(10, Unit.PX);
		frontImageUploader.setCompleteHandler(new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String url) {
				showImageItem(url);
			}
			
			@Override
			public void onFailure(Throwable arg0) {}
		});
		correctAnswerCheckBox.setValue(choice != null ? choice.isCorrect() : false);
		frontText.setContent(choice != null ? choice.getFrontText() : "");
		frontHint.setContent(choice != null ? choice.getFrontHint() : "");
		showImageItem(choice != null ? choice.getFrontImage() : "");
		setRemoveHandler(null);
		if(choice == null) {
			frontText.showEditor(true);
			frontHint.showEditor(true);
		}
	}

	public Choice getChoice() {
		if(choice == null) {
			choice = new Choice();
		}
		choice.setCorrect(correctAnswerCheckBox.getValue());
		choice.setFrontText(frontText.getContent());
		choice.setFrontHint(frontHint.getContent());
		choice.setFrontImage(frontImage);
		return choice;
	}
	
	private void showImageItem(String url) {
		if(url == null || url.isEmpty()) {
			return ;
		}
		imagesPanel.clear();
		this.frontImage = url;
		Image image = new Image(url);
		image.setWidth("100px");
		image.getElement().getStyle().setProperty("border", "1px solid #ddd");
		imagesPanel.add(image);
	}

	public void setCorrectAnswer(boolean value) {
		correctAnswerCheckBox.setValue(value);
	}
	
	public void setRemoveHandler(Callback<Void> removeHandler) {
		buttonClose.setVisible(removeHandler != null);
		if(removeHandler != null) {
			buttonClose.addClickHandler(new ClickHandler() {
				
				@Override
				public void onClick(ClickEvent arg0) {
					removeHandler.onCallback(null);
				}
			});
		}
	}
}
