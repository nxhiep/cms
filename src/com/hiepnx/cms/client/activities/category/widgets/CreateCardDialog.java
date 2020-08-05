package com.hiepnx.cms.client.activities.category.widgets;

import org.gwtbootstrap3.client.ui.Image;
import org.gwtbootstrap3.client.ui.ListBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.hiepnx.cms.client.view.BaseEditDialog;
import com.hiepnx.cms.client.view.MyEditor;
import com.hiepnx.cms.client.view.Toaster;
import com.hiepnx.cms.client.view.UploaderPanel;
import com.hiepnx.cms.shared.Config;
import com.hiepnx.cms.shared.model.Card;
import com.hiepnx.cms.shared.model.IBasic;

public class CreateCardDialog extends BaseEditDialog {

	private static CreateCardDialogUiBinder uiBinder = GWT.create(CreateCardDialogUiBinder.class);

	interface CreateCardDialogUiBinder extends UiBinder<Widget, CreateCardDialog> {
	}
	
	@UiField ListBox statusListBox;
	@UiField MyEditor frontText, frontHint;
	@UiField UploaderPanel frontImageUploader;
	@UiField FlowPanel imagesPanel;
	@UiField ChoicesPanel choicesPanel;
	private String frontImage = Config.TEXT_EMPTY;

	public CreateCardDialog() {
		setContent(uiBinder.createAndBindUi(this));
		statusListBox.addItem("Public", Config.STATUS_PUBLIC+"");
		statusListBox.addItem("Private", Config.STATUS_PRIVATE+"");
		statusListBox.addItem("Delete", Config.STATUS_DELETED+"");
		imagesPanel.getElement().getStyle().setPaddingLeft(10, Unit.PX);
		frontImageUploader.setCompleteHandler(new AsyncCallback<String>() {
			
			@Override
			public void onSuccess(String url) {
				showImageItem(url);
			}
			
			@Override
			public void onFailure(Throwable arg0) {}
		});
	}
	
	@Override
	protected void updateData(IBasic iBasic) {
		super.updateData(iBasic);
		if(iBasic == null) {
			frontText.setContent("");
			frontHint.setContent("");
			statusListBox.setSelectedIndex(0);
			frontText.showEditor(true);
			frontHint.showEditor(true);
			return;
		}
		Card card = (Card) iBasic;
		choicesPanel.setCard(card);
		frontText.setContent(card.getFrontText());
		frontHint.setContent(card.getFrontHint());
		showImageItem(card.getFrontImage());
		for (int index = 0; index < statusListBox.getItemCount(); index++) {
			if (statusListBox.getValue(index).equals(card.getStatus() + "")) {
				statusListBox.setSelectedIndex(index);
				break;
			}
		}
	}

	public boolean isValidated() {
		if(frontText.getContent() == null || frontText.getContent().isEmpty()) {
			Toaster.showError("Front Text empty!");
			return false;
		}
		return true;
	}

	public Card getCard() {
		if(iBasic == null) {
			iBasic = new Card();
		}
		Card card = (Card) iBasic;
		card.setFrontText(frontText.getContent() != null ? frontText.getContent() : "");
		card.setFrontHint(frontHint.getContent() != null ? frontHint.getContent() : "");
		card.setStatus(Integer.parseInt(statusListBox.getSelectedValue()));
		card.setFrontImage(frontImage);
		card.setChoices(choicesPanel.getChoices());
		return card;
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
}
