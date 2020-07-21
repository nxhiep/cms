package com.hiepnx.cms.shared;

public class Config {
	public static final String TEXT_EMPTY = "";
	public static final long LONG_NULL = -1;
	public static final int INT_NULL = -1;
	public static final long NULL_ID = -1l;
	
	public static final int STATUS_DELETED = -1;
	public static final int STATUS_PRIVATE = 0;
	public static final int STATUS_PUBLIC = 1;
	
	public static final int GENDER_OTHER = -1;
	public static final int GENDER_FE_MALE = 0;
	public static final int GENDER_MALE = 1;
	
	public static final int LOGIN_FAILED = -1;
	public static final int LOGIN_SUCCESS = 0;
	public static final int LOGIN_ACCOUNT_IS_USED = 1;
	public static final int LOGIN_ACCOUNT_NOT_EXIST = 2;
	public static final int LOGIN_WRONG_PASSWORD = 3;
	public static final int LOGIN_WRONG_PROVIDER = 4;
	public static final int LOGIN_ACCOUNT_NOT_ACTIVATED = 5;
	public static final int LOGIN_MOBILE_IS_USED = 6;
	
	public static final String DATE_FORMAT_FULL = "dd/MM/yyyy hh:mm:ss";
	public static final String DATE_FORMAT_DATE = "dd/MM/yyyy";

	public static final String ADMIN_PASSWORD = "abc@123";
	
	public static final int DEFAULT_EXPIRED_TIME = 30;

	public static boolean isFileNameImage(String fileName){
		if(fileName.toLowerCase().endsWith(".jpg") || fileName.toLowerCase().endsWith(".png")
			|| fileName.toLowerCase().endsWith(".gif") || fileName.toLowerCase().endsWith(".jpeg")
			|| fileName.toLowerCase().endsWith(".svg")){
			return true;
		}
		return false;
	}
	
	public static boolean isFileNameDocument(String fileName){
		if(fileName.toLowerCase().endsWith(".pdf") || fileName.toLowerCase().endsWith(".docx") || fileName.toLowerCase().endsWith(".doc")
			|| fileName.toLowerCase().endsWith(".xlsx") || fileName.toLowerCase().endsWith(".xls")
			|| fileName.toLowerCase().endsWith(".ppt") || fileName.toLowerCase().endsWith(".pptx") 
			|| fileName.toLowerCase().endsWith(".ppsx")){
			return true;
		}
		return false;
	}
	
	public static boolean isFileNameSound(String fileName){
		if(fileName.toLowerCase().endsWith(".mp3") || fileName.toLowerCase().toLowerCase().contains("wav")){
			return true;
		}
		return false;
	}
	
	public static boolean isFileNameVideo(String fileName){
		fileName.toLowerCase();
		if(fileName.endsWith(".mp4") ){
			return true;
		}
		return false;
	}
}
