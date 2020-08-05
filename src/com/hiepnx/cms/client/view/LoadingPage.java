package com.hiepnx.cms.client.view;

public class LoadingPage {
	public static void show(){
		loading(true);
	}
	
	public static void hide(){
		loading(false);
	}
	
	public static native void loading(boolean isLoading) /*-{
		if(isLoading){
			$wnd.$('#preloader').fadeIn('slow',function(){});
		} else {
			$wnd.$('#preloader').fadeOut('slow',function(){});
		}
	}-*/;
}
