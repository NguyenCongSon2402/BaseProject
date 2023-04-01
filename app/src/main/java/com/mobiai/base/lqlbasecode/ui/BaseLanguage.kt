package com.mobiai.base.lqlbasecode.ui

import androidx.appcompat.app.AppCompatActivity
import com.mobiai.R
import com.mobiai.base.lqlbasecode.language.Language

abstract class BaseLanguage : AppCompatActivity() {
    private var listLanguages: ArrayList<Language> = arrayListOf()

    private fun initData() {
        listLanguages = ArrayList()
        listLanguages.add(Language(R.drawable.flag_en, getString(R.string.language_english), "en"))
        listLanguages.add(Language(R.drawable.flag_es_spain, getString(R.string.language_spain), "es"))
        listLanguages.add(Language(R.drawable.flag_pt_portugal, getString(R.string.language_portugal), "pt"))
        listLanguages.add(Language(R.drawable.flag_in_hindi, getString(R.string.language_hindi), "in"))
        listLanguages.add(Language(R.drawable.flag_de_germany, getString(R.string.language_germany), "de"))
        listLanguages.add(Language(R.drawable.flag_fr_france, getString(R.string.language_france), "fr"))
        listLanguages.add(Language(R.drawable.flag_cn_china, getString(R.string.language_china), "cn"))
    }
}