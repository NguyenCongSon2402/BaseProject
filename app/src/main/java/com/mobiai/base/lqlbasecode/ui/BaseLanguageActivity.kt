package com.mobiai.base.lqlbasecode.ui

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ads.control.ads.AperoAd
import com.ads.control.billing.AppPurchase
import com.mobiai.R
import com.mobiai.base.lqlbasecode.language.Language
import com.mobiai.base.lqlbasecode.language.LanguageFirstOpenAdapter
import com.mobiai.base.lqlbasecode.util.SharePreferenceUtils
import com.mobiai.ui.acitivites.OnboardingActivity

abstract class BaseLanguageActivity : AppCompatActivity(),
    LanguageFirstOpenAdapter.OnLanguageClickListener {
    var languageAdapter: LanguageFirstOpenAdapter? = null
    var listLanguages: ArrayList<Language> = arrayListOf()
    var languageCode = "en"

    companion object {
        const val OPEN_FROM_MAIN = "open_from_main"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        getDataLanguage()

    }

    abstract fun initAdapter()
    override fun onClickItemListener(language: Language?) {
        languageCode = language!!.locale
    }

    fun initData() {
        listLanguages = ArrayList()
        listLanguages.add(Language(R.drawable.flag_en, getString(R.string.language_english), "en"))
        listLanguages.add(Language(R.drawable.flag_es_spain,
            getString(R.string.language_spain),
            "es"))
        listLanguages.add(Language(R.drawable.flag_pt_portugal,
            getString(R.string.language_portugal),
            "pt"))
        listLanguages.add(Language(R.drawable.flag_in_hindi,
            getString(R.string.language_hindi),
            "in"))
        listLanguages.add(Language(R.drawable.flag_de_germany,
            getString(R.string.language_germany),
            "de"))
        listLanguages.add(Language(R.drawable.flag_fr_france,
            getString(R.string.language_france),
            "fr"))
        listLanguages.add(Language(R.drawable.flag_cn_china,
            getString(R.string.language_china),
            "cn"))
    }
}