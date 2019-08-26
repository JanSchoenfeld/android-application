package fhm.wi.team5.android_application.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


/**
 * Class to manage SharedPreferences for cleaner/faster writing and reading
 *
 * @author Jan Schönfeld
 */

public class SharedPreferencesService {


    /**
     * Method to add a new Key Value pair to SharedPreferences
     *
     * @param key     Key of the new K/V pair, as String
     * @param value   Value of the new K/V pair, as String
     * @param context Context of the Activity
     */
    public static void addToSharedPreference(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Method to add a new Key Value pair to Shared Preferences
     *
     * @param key     Key of the new K/V pair, as String
     * @param value   Value of the new K/V pair, as boolean
     * @param context Context of the Activity
     */

    public static void addToSharedPreference(String key, boolean value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Method to add a new Key Value pair to Shared Preferences
     *
     * @param key     Key of the new K/V pair, as String
     * @param value   Value of the new K/V pair, as long
     * @param context Context of the Activity
     */

    public static void addToSharedPreference(String key, long value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * Method to retrieve strings from SharedPreferences
     *
     * @param key     String value of the corrosponding Key object we search
     * @param context Context of the Activity
     * @return String value of the Key, with "" if no value found
     */
    public static String retrieveStringFromSharedPreference(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, "");
    }

    /**
     * Method to retrieve boolean values from SharedPreferences
     *
     * @param key     String value of the corrosponding Key object we search
     * @param context Context of the Activity
     * @return Boolean value of the Key, with false if no value found
     */
    public static boolean retrieveBooleanFromSharedPreference(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getBoolean(key, false);
    }

    /**
     * Method to retrieve long values from SharedPreferences
     *
     * @param key     String name of the corrosponding Key object we search
     * @param context Context of the Activity
     * @return Long value of the Key, with 0 if no value found
     */
    public static long retrieveLongFromSharedPreference(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getLong(key, 0);
    }

    /**
     * Method to remove values from SharedPreferences
     *
     * @param key     Key of the value that needs removal
     * @param context Context of the Activity
     */
    public static void removeFromSharedPreferences(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().remove(key).apply();
    }
}
