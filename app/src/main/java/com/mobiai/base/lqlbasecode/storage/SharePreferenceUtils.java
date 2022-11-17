package com.mobiai.base.lqlbasecode.storage;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;

public class SharePreferenceUtils {

    public static String getLanguageCode(Context context) {
        SharedPreferences pre = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        return pre.getString("language", Locale.getDefault().getLanguage());
    }

    public static String getLanguageCode(Context context, String defaultLang) {
        SharedPreferences pre = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        return pre.getString("language", defaultLang);
    }

    public static void saveLanguageCode(Context context, String language) {
        SharedPreferences pre = context.getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();
        editor.putString("language", language);
        editor.apply();
    }
}
