package com.mobiai.base.lqlbasecode.language

import android.content.Context
import android.content.res.Configuration
import com.mobiai.base.lqlbasecode.storage.SharePreferenceUtils
import java.util.*

object LanguageUtil {

    fun getSavedLocal(context: Context) : String{
        return SharePreferenceUtils.getLanguageCode(context)
    }

    fun setLocale(context: Context, languageCode: String) {
        val config = Configuration()
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        config.locale = locale
        context.resources
            .updateConfiguration(config, null)

        SharePreferenceUtils.saveLanguageCode(context, languageCode)
    }

    fun setSavedLocal(context: Context) {
        val languageCode = SharePreferenceUtils.getLanguageCode(context)
        setLocale(context, languageCode)
    }

    fun setupLanguage(context: Context) {
        val languageCode = SharePreferenceUtils.getLanguageCode(context)

        val config = Configuration()
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        config.locale = locale
        context.getResources()
            .updateConfiguration(config, null)
    }
}
