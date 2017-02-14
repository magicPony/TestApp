package com.example.taras.testapp.dataStoreApi;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Taras on 12/02/2017.
 */

public class PrefsApi {

    private static String APP_PREFS = "prefs";

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
    }

    public static void putString(Context context, String key, String val) {
        SharedPreferences prefs = getPrefs(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, val);
        editor.apply();
    }

    public static void putInt(Context context, String key, int val) {
        SharedPreferences prefs = getPrefs(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, val);
        editor.apply();
    }

    public static String getString(Context context, String key, String defVal) {
        SharedPreferences prefs = getPrefs(context);
        return prefs.getString(key, defVal);
    }

    public static int getInt(Context context, String key, int defVal) {
        SharedPreferences prefs = getPrefs(context);
        return prefs.getInt(key, defVal);
    }
}
