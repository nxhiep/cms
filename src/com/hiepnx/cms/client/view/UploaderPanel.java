package com.hiepnx.cms.client.view;

import org.moxieapps.gwt.uploader.client.Uploader;
import org.moxieapps.gwt.uploader.client.events.FileDialogCompleteEvent;
import org.moxieapps.gwt.uploader.client.events.FileDialogCompleteHandler;
import org.moxieapps.gwt.uploader.client.events.FileDialogStartEvent;
import org.moxieapps.gwt.uploader.client.events.FileDialogStartHandler;
import org.moxieapps.gwt.uploader.client.events.UploadErrorEvent;
import org.moxieapps.gwt.uploader.client.events.UploadErrorHandler;
import org.moxieapps.gwt.uploader.client.events.UploadProgressEvent;
import org.moxieapps.gwt.uploader.client.events.UploadProgressHandler;
import org.moxieapps.gwt.uploader.client.events.UploadSuccessEvent;
import org.moxieapps.gwt.uploader.client.events.UploadSuccessHandler;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlowPanel;

public class UploaderPanel extends FlowPanel {

	protected Uploader uploader = new Uploader();
	protected AsyncCallback<String> callback;
	private final String iconUploadDefault = "<i class=\"fa fa-cloud-upload\" aria-hidden=\"true\"></i>";
	
	protected void setButtonUpload(String data, String buttonType) {
		uploader.setButtonText("<button class=\"btn " + (buttonType != null && !buttonType.isEmpty() ? buttonType : "btn-success") + " upload-icon\">"+data+"</button>");
	}
	
	public UploaderPanel() {
		this.add(uploader);
		this.setStyleName("upload-panel");
		resetUploaderButton(0);
		uploader.setUploadURL("/file-upload");
		bind();
		uploader.setButtonDisabled(false);
	}

	private void bind() {
		uploader.setFileDialogStartHandler(new FileDialogStartHandler() {
			
			@Override
			public boolean onFileDialogStartEvent(FileDialogStartEvent fileDialogStartEvent) {
//				ClientUtils.log("setFileDialogStartHandler");
				uploader.setButtonDisabled(true);
				resetUploaderButton(0);
				return true;
			}
		});
		uploader.setFileDialogCompleteHandler(new FileDialogCompleteHandler() {
			public boolean onFileDialogComplete(FileDialogCompleteEvent dialogCompleteEvent) {
				uploader.setButtonDisabled(false);
				if (dialogCompleteEvent.getTotalFilesInQueue() > 0) {
					uploader.startUpload();
				}
				return true;
			}
		});
		uploader.setUploadSuccessHandler(new UploadSuccessHandler() {
			
			@Override
			public boolean onUploadSuccess(UploadSuccessEvent uploadSuccessEvent) {
				uploader.setButtonDisabled(false);
				String url = uploadSuccessEvent.getServerData();
				Window.alert("Response from server was: url = " + url);
				if(callback != null) {
					callback.onSuccess(url);
				}
				Toaster.showSuccess("Uploaded!");
				return true;
			}
		});
		uploader.setUploadErrorHandler(new UploadErrorHandler() {
			
			@Override
			public boolean onUploadError(UploadErrorEvent uploadErrorEvent) {
//				ClientUtils.log("setUploadErrorHandler");
				if(callback != null) {
					callback.onFailure(new Throwable(uploadErrorEvent.getMessage()));
				}
				uploader.setButtonDisabled(true);
				Toaster.showError(uploadErrorEvent.getMessage());
				setButtonUpload("<i class=\"fa fa-times\"></i>", "red");
				resetUploaderButton(5000);
				return false;
			}
		});
		uploader.setUploadProgressHandler(new UploadProgressHandler() {
			public boolean onUploadProgress(UploadProgressEvent uploadProgressEvent) {
//				ClientUtils.log("setUploadProgressHandler");
				uploader.setButtonDisabled(true);
				float percent = ((float) uploadProgressEvent.getBytesComplete() / uploadProgressEvent.getBytesTotal()) * 100;
				if(percent < 100) {
					setButtonUpload(percent + "%", null);
				} else {
					setButtonUpload("<i class=\"fa fa-check\"></i>", null);
					resetUploaderButton(5000);
				}
				return true;
			}
		});

	}
	
	protected void resetUploaderButton(int time) {
		if(time <= 0) {
			setButtonUpload(iconUploadDefault, null);
		} else {
			new Timer() {
				
				@Override
				public void run() {
					uploader.setButtonDisabled(false);
					setButtonUpload(iconUploadDefault, null);
				}
			}.schedule(time);
		}
	}

	public void setCompleteHandler(AsyncCallback<String> callback) {
		this.callback = callback;
	}
}