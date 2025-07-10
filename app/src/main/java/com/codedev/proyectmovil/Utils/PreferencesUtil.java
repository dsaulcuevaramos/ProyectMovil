package com.codedev.proyectmovil.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesUtil {
    private static final String PREFER_NAME = "mis_preferencias";

    public static void saveKey(Context context, String clave, String valor){
        SharedPreferences pref = context.getSharedPreferences(PREFER_NAME,Context.MODE_PRIVATE);
        pref.edit().putString(clave, valor).apply();
    }

    public static String getKey(Context context, String clave){
        SharedPreferences pref = context.getSharedPreferences(PREFER_NAME,Context.MODE_PRIVATE);
        return pref.getString(clave, "");
    }

    public static void deleteKey(Context context, String clave){
        SharedPreferences pref = context.getSharedPreferences(PREFER_NAME,Context.MODE_PRIVATE);
        pref.edit().remove(clave).apply();
    }

    public static void clearAllKeys(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREFER_NAME,Context.MODE_PRIVATE);
        pref.edit().clear().apply();
    }
}
