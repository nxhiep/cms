package com.hiepnx.cms.shared;

public class Utils {

	public static boolean isAudio(String contentType) {
		if(contentType == null) {
			return false;
		}
		return contentType.startsWith("audio");
	}
	
	public static boolean isVideo(String contentType) {
		if(contentType == null) {
			return false;
		}
		return contentType.startsWith("video");
	}

	public static boolean isDocument(String contentType) {
		if(contentType == null) {
			return false;
		}
		return contentType.startsWith("application") || contentType.startsWith("text");
	}
	
	public static boolean isImage(String contentType) {
		if(contentType == null) {
			return false;
		}
		return contentType.startsWith("image");
	}
}
