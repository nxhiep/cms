package com.hiepnx.cms.shared;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
	
	public static String getRequestBody(HttpServletRequest request) {
		final StringBuilder builder = new StringBuilder();
		try (BufferedReader reader = request.getReader()) {
			if (reader == null) {
				System.out.println("Request body could not be read because it's empty.");
				return null;
			}
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			return builder.toString();
		} catch (final Exception e) {
			System.out.println("Could not obtain the saml request body from the http request " + e.getMessage());
			return null;
		}
	}
	
	public static long getLongValue(JsonObject object, String key) {
		long value = -1;
		if (object != null && object.has(key)) {
			try {
				value = object.get(key).getAsLong();
			} catch (Exception e) {
			}
		}
		return value;
	}

	public static int getIntValue(JsonObject object, String key) {
		int value = -1;
		if (object != null && object.has(key)) {
			try {
				value = object.get(key).getAsInt();
			} catch (Exception e) {
			}
		}
		return value;
	}

	public static String getStringValue(JsonObject object, String key) {
		String value = "";
		if (object != null && object.has(key)) {
			try {
				value = object.get(key).getAsString();
			} catch (Exception e) {
			}
		}
		return value;
	}

	public static double getDoubleValue(JsonObject object, String key) {
		double value = -1;
		if (object != null && object.has(key)) {
			try {
				value = object.get(key).getAsDouble();
			} catch (Exception e) {
			}
		}
		return value;
	}

	public static List<String> getListStringValue(JsonObject object, String key) {
		List<String> values = new ArrayList<String>();
		if (object != null && object.has(key)) {
			try {
				JsonArray array = new JsonArray();
				if(object.get(key).isJsonArray()) {
					array = object.get(key).getAsJsonArray();
				} else {
					array = new JsonParser().parse(object.get(key).getAsString()).getAsJsonArray();
				}
				for (int i = 0; i < array.size(); i++) {
					String str = array.get(i).getAsString();
					if(str != null && !str.isEmpty()) {
						values.add(str);
					}
				}
			} catch (Exception e) {
			}
		}
		return values;
	}

	public static List<Long> getListLongValue(JsonObject object, String key) {
		List<Long> values = new ArrayList<Long>();
		if (object != null && object.has(key)) {
			try {
				JsonArray array = new JsonArray();
				if(object.get(key).isJsonArray()) {
					array = object.get(key).getAsJsonArray();
				} else {
					array = new JsonParser().parse(object.get(key).getAsString()).getAsJsonArray();
				}
				for (int i = 0; i < array.size(); i++) {
					Long v = array.get(i).getAsLong();
					if (v != null) {
						values.add(v);
					}
				}
			} catch (Exception e) {
			}
		}
		return values;
	}

	public static List<Integer> getListIntegerValue(JsonObject object, String key) {
		List<Integer> values = new ArrayList<Integer>();
		if (object != null && object.has(key)) {
			try {
				JsonArray array = new JsonArray();
				if (object.get(key).isJsonArray()) {
					array = object.get(key).getAsJsonArray();
				} else {
					array = new JsonParser().parse(object.get(key).getAsString()).getAsJsonArray();
				}
				for (int i = 0; i < array.size(); i++) {
					values.add(array.get(i).getAsInt());
				}
			} catch (Exception e) {
			}
		}
		return values;
	}

	public static boolean getBooleanValue(JsonObject object, String key) {
		boolean value = false;
		if (object != null && object.has(key)) {
			try {
				value = object.get(key).getAsBoolean();
			} catch (Exception e) {
			}
		}
		return value;
	}
	
	public static String getStringValue(HttpServletRequest request, String key) {
		return request.getParameter(key);
	}

	public static boolean getBooleanValue(HttpServletRequest request, String key) {
		String value = request.getParameter(key);
		return value != null && value.equals("true");
	}

	public static long getLongValue(HttpServletRequest request, String key) {
		String value = request.getParameter(key);
		long result = Config.NULL_ID;
		try {
			result = Long.parseLong(value);
		} catch (Exception e) {
		}
		return result;
	}

	public static int getIntValue(HttpServletRequest request, String key) {
		String value = request.getParameter(key);
		int result = -1;
		try {
			result = Integer.parseInt(value);
		} catch (Exception e) {
		}
		return result;
	}
	
	public static List<Integer> getListIntValue(HttpServletRequest request, String key) {
		String value = request.getParameter(key);
		List<Integer> values = new ArrayList<Integer>();
		try {
			JsonArray array = new JsonParser().parse(value).getAsJsonArray();
			if (array != null && array.isJsonArray()) {
				for (int i = 0; i < array.size(); i++) {
					values.add(array.get(i).getAsInt());
				}
			}
		} catch (Exception e) {}
		return values;
	}

	public static long getParamsId(HttpServletRequest req, String key) {
		String idStr = req.getParameter(key);
		if (idStr != null && !idStr.isEmpty()) {
			try {
				return Long.parseLong(idStr);
			} catch (Exception e) {
			}
		}
		return Config.NULL_ID;
	}

	public static int getParamsInt(HttpServletRequest req, String key) {
		String idStr = req.getParameter(key);
		if (idStr != null && !idStr.isEmpty()) {
			try {
				return Integer.parseInt(idStr);
			} catch (Exception e) {
			}
		}
		return -1;
	}
}
