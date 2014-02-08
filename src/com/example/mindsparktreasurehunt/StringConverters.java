package com.example.mindsparktreasurehunt;

import java.util.Locale;

public class StringConverters {
	
	public static String firstCharacterLowerCase(String str) {
	    char[] chars = str.toCharArray();
	    chars[0] = Character.toLowerCase(chars[0]);
	    return new String(chars);
	}
	
	public static String firstCharacterUpperCase(String str) {
	    char[] chars = str.toCharArray();
	    chars[0] = Character.toUpperCase(chars[0]);
	    return new String(chars);
	}
	
	public static String underscoreCase(String str) {
		String regex = "([a-z])([A-Z]+)";
		String replacement = "$1_$2";
		return str.replaceAll(regex, replacement).toLowerCase(Locale.ENGLISH);
	}
	
	public static String camelCase(String str) {
		return capitalizedString(str).replace("_", "");
	}
	
	public static String camelCaseLower(String str) {
		return firstCharacterLowerCase(capitalizedString(str).replace("_", ""));
	}
	
	public static String capitalizedString(String str) {
	    boolean prevWasWhiteSp = true;
	    char[] chars = str.toCharArray();
	    for (int i = 0; i < chars.length; i++) {
	        if (Character.isLetter(chars[i])) {
	            if (prevWasWhiteSp) {
	                chars[i] = Character.toUpperCase(chars[i]);    
	            }
	            prevWasWhiteSp = false;
	        } else {
	            prevWasWhiteSp = Character.isWhitespace(chars[i]);
	        }
	    }
	    return new String(chars);
	}
	
	public static String pluralized(String str) {
		if (str.endsWith("s")) {
			return str;
		} else {
			return str + "s";
		}
	}
	
	public static String singularize(String str) {
		if (str.endsWith("s")) {
			return str.substring(0, str.length() - 1);
		} else {
			return str;
		}
	}
	
}
