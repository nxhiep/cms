package com.hiepnx.cms.client.view;

import org.gwtbootstrap3.extras.summernote.client.ui.Summernote;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;

public class MyEditor extends FlowPanel {

	private HTML statusHTML = new HTML("Edit");
	private FlowPanel editorPanel = new FlowPanel();
	private Summernote summerNote = new Summernote();
	private HTML contentHTML = new HTML();
	
	public MyEditor() {
		contentHTML.getElement().getStyle().setProperty("border", "1px solid #ddd");
		contentHTML.getElement().getStyle().setPadding(5, Unit.PX);
		contentHTML.getElement().getStyle().setProperty("borderRadius", "3px");
		contentHTML.setVisible(true);
		editorPanel.setVisible(false);
		this.add(statusHTML);
		this.add(contentHTML);
		this.add(editorPanel);
		editorPanel.add(summerNote);
		statusHTML.setStyleName("div-anchor");
		bind();
	}
	
	private void bind() {
		contentHTML.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				showEditor(true);
			}
		});
		statusHTML.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				showEditor(!editorPanel.isVisible());
			}
		});
	}
	
	public void showEditor(boolean isShowEditor) {
		contentHTML.setVisible(!isShowEditor);
		statusHTML.setHTML(isShowEditor ? "Save" : "Edit");
		editorPanel.setVisible(isShowEditor);
		if(isShowEditor) {
			new Timer() {
				
				@Override
				public void run() {
					summerNote.setHasFocus(true);
				}
			}.schedule(300);
		} else {
			String content = getContent();
			if(content == null || content.isEmpty()) {
				content = "&nbsp;";
			}
			contentHTML.setHTML(content);
		}
	}

	public String getContent() {
		return summerNote.getCode();
	}

	public void setContent(String content) {
		summerNote.setCode(content);
		contentHTML.setHTML(content == null || content.isEmpty() ? "&nbsp;" : content);
	}

	public Summernote getSummerNote() {
		return summerNote;
	}
}