package com.mobiai.ui.activities

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ads.control.ads.AperoAd
import com.ads.control.billing.AppPurchase
import com.mobiai.App
import com.mobiai.R
import com.mobiai.base.lqlbasecode.language.Language
import com.mobiai.base.lqlbasecode.language.LanguageFirstOpenAdapter
import com.mobiai.base.lqlbasecode.ui.BaseLanguageActivity
import com.mobiai.base.lqlbasecode.util.LanguageUtils
import com.mobiai.base.lqlbasecode.util.SharePreferenceUtils
import com.mobiai.databinding.ActivityLanguageBinding
import com.mobiai.ui.acitivites.MainActivity

class LanguageActivity : BaseLanguageActivity() {
    lateinit var binding: ActivityLanguageBinding

    companion object {
        private const val INTENT_DATA = "intent_data"
        fun start(context: Context, isOpenFromMain: Boolean = false, path: String? = null) {
            Intent(context, LanguageActivity::class.java).apply {
                putExtra(OPEN_FROM_MAIN, isOpenFromMain)
                putExtra(INTENT_DATA, path)
                context.startActivity(this)
            }
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getDataLanguage()
        if (!intent.getBooleanExtra(OPEN_FROM_MAIN, false)){ //&& FirebaseUtils.getShowLanguageFirstOpen(this)) {
            initAds()
        } else {
            binding.frAds.visibility = View.GONE
        }
        binding.imgConfirm.setOnClickListener {
            changeLanguage()
        }
    }

    override fun initAdapter() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewLanguage.layoutManager = layoutManager
        languageAdapter = LanguageFirstOpenAdapter(this, listLanguages, this)
        binding.recyclerViewLanguage.adapter = languageAdapter
    }

    private fun changeLanguage() {
        SharePreferenceUtils.setLanguage(this, languageCode)
        LanguageUtils.changeLang(SharePreferenceUtils.getLanguage(this), this)
        SharePreferenceUtils.setFirstOpenApp(this, false)
        //todo action complete onboarding
//        if (!SharePreferenceUtils.getCompleteOnBoarding(this)) {
//                OnBoardingActivity.start(this)
//        } else {
            MainActivity.startMain(this, true)
//        }
        finish()
    }
    private fun initAds() {
        if (AppPurchase.getInstance().isPurchased) {
            binding.frAds.visibility = View.GONE
        } else {
            App.getStorageCommon()?.nativeAdLanguage?.observe(this) {
                if (it != null) {
                    AperoAd.getInstance().populateNativeAdView(
                        this,
                        it,
                        binding.frAds,
                        binding.includeNative.shimmerContainerNative
                    )
                } else {
                    binding.frAds.visibility = View.GONE
                }
            }
        }
    }

    private fun getDataLanguage() {
        initData()
        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Resources.getSystem().configuration.locales.get(0)
        } else {
            Resources.getSystem().configuration.locale
        }
        var languageSystem: Language? = null
        var position = 0
        for (language in listLanguages) {
            if (language.locale.equals(locale.language)) {
                languageSystem = language
                languageCode = locale.language
            }
            if (intent.getBooleanExtra(OPEN_FROM_MAIN, false)) {
                if (SharePreferenceUtils.getLanguage(this) == language.locale) {
                    languageSystem = language
                    onClickItemListener(languageSystem)
                }
                position = listLanguages.indexOf(languageSystem)
            }
        }
        listLanguages[position].isChoose = true
        initAdapter()
    }
}