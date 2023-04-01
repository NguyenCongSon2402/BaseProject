package com.mobiai.base.lqlbasecode.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Locale;

public class SharePreferenceUtils {
    private static final String ACHIEVER_SHARE_PRE = "achiever_share_pre";
    private static final String KEY_COMPLETE_ON_BOARDING = "KEY_COMPLETE_ON_BOARDING";
    public static final String KEY_LANGUAGE = "language";
    public static final String KEY_FIRST_OPEN_APP = "KEY_FIRST_OPEN_APP";
    public static final String KEY_OUTPUT_NAME_SET = "KEY_OUTPUT_NAME_SET";

    public static void setLanguage(Context context, String language) {
        SharedPreferences pre = context.getSharedPreferences(ACHIEVER_SHARE_PRE, Context.MODE_PRIVATE);
        pre.edit().putString(KEY_LANGUAGE, language).apply();
    }

    public static String getLanguage(Context context) {
        SharedPreferences pre = context.getSharedPreferences(ACHIEVER_SHARE_PRE, Context.MODE_PRIVATE);
        return pre.getString(KEY_LANGUAGE, null);
    }

    public static void setFirstOpenApp(Context context, boolean isFirst) {
        SharedPreferences pre = context.getSharedPreferences(ACHIEVER_SHARE_PRE, Context.MODE_PRIVATE);
        pre.edit().putBoolean(KEY_FIRST_OPEN_APP, isFirst).apply();
    }

    public static boolean getFirstOpenApp(Context context) {
        SharedPreferences pre = context.getSharedPreferences(ACHIEVER_SHARE_PRE, Context.MODE_PRIVATE);
        return pre.getBoolean(KEY_FIRST_OPEN_APP, true);
    }
    public static void setCompleteOnBoarding(Context context, boolean isComplete) {
        SharedPreferences pre = context.getSharedPreferences(ACHIEVER_SHARE_PRE, Context.MODE_PRIVATE);
        pre.edit().putBoolean(KEY_COMPLETE_ON_BOARDING, isComplete).apply();
    }
    public static boolean getCompleteOnBoarding(Context context) {
        SharedPreferences pre = context.getSharedPreferences(ACHIEVER_SHARE_PRE, Context.MODE_PRIVATE);
        return pre.getBoolean(KEY_COMPLETE_ON_BOARDING, false);
    }

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
