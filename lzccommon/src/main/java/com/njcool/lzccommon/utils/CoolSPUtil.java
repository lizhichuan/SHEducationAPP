package com.njcool.lzccommon.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;

public class CoolSPUtil {
	

	
	// SharePreference用法
	public static void insertDataToLoacl(Context context, String key, String value) {
		SharedPreferences settings = context.getSharedPreferences("spXML", 0);
		SharedPreferences.Editor localEditor = settings.edit();
		localEditor.putString(key, value);
		localEditor.commit();
	}
	// SharePreference用法
	public static String getDataFromLoacl(Context context, String key) {
		SharedPreferences settings = context.getSharedPreferences("spXML", 0);
		return settings.getString(key,"");
	}
	// SharePreference用法
	public static void clearDataFromLoacl(Context context) {
		SharedPreferences settings = context.getSharedPreferences("spXML", 0);
		SharedPreferences.Editor localEditor = settings.edit();
		localEditor.clear().commit();
	}

	// SharePreference用法
	public static void insertDataToLoacl(Context context, String key, HashSet<String> cookies) {
		SharedPreferences settings = context.getSharedPreferences("spXML", 0);
		SharedPreferences.Editor localEditor = settings.edit();
		localEditor.putStringSet(key, cookies);
		localEditor.commit();
	}

	// SharePreference用法
	public static HashSet<String> getDataFromLoaclSet(Context context, String key) {
		SharedPreferences settings = context.getSharedPreferences("spXML", 0);
		return (HashSet<String>) settings.getStringSet(key, new HashSet<String>());
	}

}
