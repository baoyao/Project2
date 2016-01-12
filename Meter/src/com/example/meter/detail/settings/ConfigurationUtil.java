package com.example.meter.detail.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class ConfigurationUtil {

	public static final String SHARED_KEY_SPEED = "speed";
	public static final String SHARED_KEY_MODE = "mode";
	public static final String SHARED_KEY_CONTROL = "control";
	public static final String SHARED_KEY_BRAKE = "brake";
	public static final String SHARED_KEY_LIGHT_HEAD = "light_head";
	public static final String SHARED_KEY_LIGHT_DAY = "light_day";
	public static final String SHARED_KEY_LOCK = "lock";

	public static final int SPEED_1 = 1;
	public static final int SPEED_2 = 2;
	public static final int SPEED_3 = 3;
	public static final int SPEED_4 = 4;
	public static final int SPEED_5 = 5;

	public static final int MODE_1_C = 1;
	public static final int MODE_2_N = 2;
	public static final int MODE_3_D = 3;

	public static final int CONTROL_1_MANUAL = 1;
	public static final int CONTROL_2_REMOTE = 2;
	public static final int CONTROL_3_PC = 3;

	public static final int BRAKE_MANUAL = 1;
	public static final int BRAKE_AUTO = 2;
	

	public static final int LIGHT_HEAD_OFF = 1;
	public static final int LIGHT_HEAD_NO = 2;

	public static final int LIGHT_DAY_OFF = 1;
	public static final int LIGHT_DAY_NO = 2;

	private final String SHARED_PREFERENCES_NAME = "shared_preferences";
	private static ConfigurationUtil mConfigurationUtil;
	private Context mContext;

	private ConfigurationUtil(Context context) {
		mContext = context;
	}

	public static ConfigurationUtil getInstance(Context context) {
		if (mConfigurationUtil == null) {
			mConfigurationUtil = new ConfigurationUtil(context);
		}
		return mConfigurationUtil;
	}

	public boolean saveConfiguration(String key, int value) {
		try {
			SharedPreferences preferences = mContext.getSharedPreferences(
					SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
			Editor editor = preferences.edit();
			editor.putInt(key, value);
			editor.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean saveConfiguration(String key, String value) {
		try {
			SharedPreferences preferences = mContext.getSharedPreferences(
					SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
			Editor editor = preferences.edit();
			editor.putString(key, value);
			editor.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public int getConfigurationForInt(String key) {
		SharedPreferences preferences = mContext.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		int value = preferences.getInt(key, 1);
		return value;
	}

	public String getConfigurationForString(String key) {
		SharedPreferences preferences = mContext.getSharedPreferences(
				SHARED_PREFERENCES_NAME, Context.MODE_WORLD_WRITEABLE);
		String value = preferences.getString(key, null);
		return value;
	}

}
