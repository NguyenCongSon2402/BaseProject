package com.mobiai

import android.annotation.SuppressLint
import com.ads.control.admob.Admob
import com.ads.control.admob.AppOpenManager
import com.ads.control.ads.AperoAd
import com.ads.control.ads.AperoAdConfig
import com.ads.control.application.AdsMultiDexApplication
import com.google.android.gms.ads.AdActivity
import com.mobiai.ui.SplashActivity
import com.mobiai.ui.storage.StorageCommon
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

open class App : AdsMultiDexApplication() {
    companion object {

        private var storageCommon: StorageCommon? = null

        fun getStorageCommon(): StorageCommon? {
            return storageCommon
        }

        private var instance: App? = null

        fun getInstance(): App? {
            return instance
        }
    }

    @SuppressLint("HardwareIds")
    override fun onCreate() {
        super.onCreate()
        instance = this
        storageCommon = StorageCommon()

//        checkUseEnglish()
//        Reprint.initialize(this)
        initFont()
//        val readerConfig = ReaderConfiguration(ReaderConfiguration.PDF_WITHOUT_MU)
//        ReaderManager.getInstance().init(readerConfig)

        initAds()

    }

    open fun initAds() {
        AppOpenManager.getInstance().disableAppResumeWithActivity(AdActivity::class.java)
        AppOpenManager.getInstance().disableAppResumeWithActivity(SplashActivity::class.java)
        Admob.getInstance().setDisableAdResumeWhenClickAds(true)
        aperoAdConfig.mediationProvider = AperoAdConfig.PROVIDER_ADMOB
        aperoAdConfig.setVariant(BuildConfig.build_debug)
        aperoAdConfig.idAdResume =BuildConfig.openApp_resume
        aperoAdConfig.enableAdjust("idiijt1n3hts")
        aperoAdConfig.listDeviceTest = getDeviceList()
        AperoAd.getInstance().init(this, aperoAdConfig, false)
    }


    private fun getDeviceList(): List<String> {
        return arrayListOf("F6EB5D0CC9B070A45AB281D752FA154C","734EFEEF596171DA85C77592C98499AE")
    }

    private fun initFont() {
        CalligraphyConfig.initDefault(
            CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/gilroy_regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        )
    }


}