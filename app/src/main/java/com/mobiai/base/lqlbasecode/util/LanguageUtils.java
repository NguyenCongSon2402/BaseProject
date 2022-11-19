package com.mobiai.base.lqlbasecode.util;

import android.content.Context;
import android.content.res.Configuration;
import android.text.TextUtils;

import java.util.Locale;

public class LanguageUtils {

    public static void saveLocale(String lang, Context context) {
        SharePreferenceUtils.setLanguage(context, lang);
    }

    public static void changeLang(String lang, Context context) {
        if (lang.equalsIgnoreCase(""))
            return;
        Locale myLocale = new Locale(lang);
        saveLocale(lang, context);
        Locale.setDefault(myLocale);
        Configuration config = new Configuration();
        config.locale = myLocale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    public static void setupLanguage(Context context) {
        String languageCode = SharePreferenceUtils.getLanguage(context);
        if (TextUtils.isEmpty(languageCode)) languageCode = Locale.getDefault().getLanguage();

        Configuration config = new Configuration();
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);

        config.locale = locale;
        context.getResources()
            .updateConfiguration(config, null);
    }
}
