package com.app.framework.sharedpref;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by LJTat on 2/23/2017.
 */
public class SharedPref {
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.Editor prefsEditor;

    /**
     * @param context  Interface to global information about an application environment
     * @param prefName : Name of Preference
     */
    @SuppressLint("CommitPrefEdits")
    public SharedPref(@NonNull Context context, String prefName) {
        this.sharedPreferences = context.getSharedPreferences(prefName, Activity.MODE_PRIVATE);
        this.prefsEditor = sharedPreferences.edit();
    }

    /**
     * Method for clearing all data of preference
     */
    public void clearAllPreferences() {
        prefsEditor.clear();
        prefsEditor.commit();
    }

    /**
     * Method for remove data of preference
     *
     * @param key The name of the preference to remove
     */
    public void removePreference(String key) {
        prefsEditor.remove(key);
        prefsEditor.commit();
    }

    /**
     * @param key   The name of the preference to remove
     * @param value The new value for the preference
     */
    public void setPref(String key, String value) {
        prefsEditor.putString(key, value);
        prefsEditor.commit();
    }

    /**
     * @param key   The name of the preference to remove
     * @param value The new value for the preference
     */
    public void setPref(String key, int value) {
        prefsEditor.putInt(key, value);
        prefsEditor.commit();
    }

    /**
     * @param key   The name of the preference to remove
     * @param value The new value for the preference
     */
    public void setPref(String key, long value) {
        prefsEditor.putLong(key, value);
        prefsEditor.commit();
    }

    /**
     * @param key   The name of the preference to remove
     * @param value The new value for the preference
     */
    public void setPref(String key, boolean value) {
        prefsEditor.putBoolean(key, value);
        prefsEditor.commit();
    }

    /**
     * @param key   The name of the preference to remove
     * @param value The new value for the preference
     * @return String value
     */
    @Nullable
    public String getStringPref(String key, String value) {
        return sharedPreferences.getString(key, value);
    }

    /**
     * @param key   The name of the preference to remove
     * @param value The new value for the preference
     * @return int value
     */
    public int getIntPref(String key, int value) {
        return sharedPreferences.getInt(key, value);
    }

    /**
     * @param key   The name of the preference to remove
     * @param value The new value for the preference
     * @return boolean value
     */
    public boolean getBooleanPref(String key, boolean value) {
        return sharedPreferences.getBoolean(key, value);
    }

    /**
     * @param key   The name of the preference to remove
     * @param value The new value for the preference
     * @return long value
     */
    public long getLongPref(String key, long value) {
        return sharedPreferences.getLong(key, value);
    }

}
