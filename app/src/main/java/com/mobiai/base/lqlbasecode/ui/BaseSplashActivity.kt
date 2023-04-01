package com.mobiai.base.lqlbasecode.ui

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.PowerManager
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ads.control.ads.AperoAd
import com.ads.control.ads.AperoAdCallback
import com.ads.control.ads.wrapper.ApAdError
import com.ads.control.ads.wrapper.ApNativeAd
import com.ads.control.billing.AppPurchase
import com.mobiai.App
import com.mobiai.BuildConfig
import com.mobiai.R
import com.mobiai.base.lqlbasecode.ultility.UriUtils
import com.mobiai.base.lqlbasecode.util.SharePreferenceUtils
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

abstract class BaseSplashActivity : AppCompatActivity() {

    private var screenLock: PowerManager.WakeLock? = null
    private var startLoadSplashAds: Long = 0
    private var isFetchedRemote = false
    protected val ADS_DELAY = 3000 // milliseconds
    private var isLoadedSplash = false
    private var TAG = BaseSplashActivity::class.java.simpleName
    var isOnStop = false

    abstract fun getIdAdsSplash(): String
    abstract fun startMain()


    @SuppressLint("InvalidWakeLockTag")
    protected fun wakeUpScreen() {
        screenLock =
            (getSystemService(POWER_SERVICE) as PowerManager).newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
                "mywakelocktag")

        screenLock?.acquire(3 * 60 * 1000L)
    }

    protected fun releaseWakeUp() {
        screenLock?.let {
            if (it.isHeld) it.release()
        }

        try {
            screenLock?.let {
                if (it.isHeld) it.release()
            }
        } catch (e: Exception) {

        }
    }

    protected fun getPathFromData(): String? {
        return try {
            UriUtils.getPathFromUriTryMyBest(this, intent.data)
        } catch (e: Exception) {
            null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getKeyHash()
        initAdsNativeLanguage()
    }

    override fun onResume() {
        super.onResume()
        window.statusBarColor = Color.WHITE

    }

    private fun getKeyHash() {
        try {
            val info = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", android.util.Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
        } catch (e: NoSuchAlgorithmException) {
        }
    }


    protected fun loadAdsSplash() {
        startLoadSplashAds = System.currentTimeMillis()
        if (!AppPurchase.getInstance().isPurchased) {
            AperoAd.getInstance().loadSplashInterstitialAds(
                this,
                getIdAdsSplash(),
                15000,
                5000,
                false,
                adCallBack
            )
        }
    }


    private var adCallBack = object : AperoAdCallback() {
        override fun onAdSplashReady() {
            super.onAdSplashReady()
            Log.i(TAG, "AperoAdCallback - onAdSplashReady: isFetchedRemote=$isFetchedRemote")
            isLoadedSplash = true
//            if (isFetchedRemote) {
            navigateToMain()
//            }
        }

        override fun onAdFailedToLoad(adError: ApAdError?) {
            super.onAdFailedToLoad(adError)
            isLoadedSplash = true
            Log.i(TAG,
                "AperoAdCallback - onAdFailedToShow isFetchedRemote=$isFetchedRemote, ${adError?.message}")
//            if (isFetchedRemote) {
            navigateToMain()
//            }
        }

        override fun onNextAction() {
            super.onNextAction()
            Log.i(TAG, "AperoAdCallback - onNextAction")
        }

        override fun onAdFailedToShow(adError: ApAdError?) {
            super.onAdFailedToShow(adError)
            Log.i(TAG, "AperoAdCallback - onAdFailedToShow ${adError?.message}")
        }

    }

    private fun executeNavigateToMain() {
        if (AppPurchase.getInstance().isPurchased(this)) {
            if (!isDestroyed) {
                startMain()
            }
            return
        }
        AperoAd.getInstance().onShowSplash(
            this,
            object : AperoAdCallback() {
                override fun onNextAction() {
                    super.onNextAction()
                    Log.i(TAG, "onShowSplash - onNextAction")
                    if (isOnStop || (isFinishing || isDestroyed)) return
                    startMain()
                }

                override fun onAdFailedToShow(adError: ApAdError?) {
                    super.onAdFailedToShow(adError)
                    Log.i(TAG, "onShowSplash - onAdFailedToShow ${adError?.message}")
                    if (isOnStop || (isFinishing || isDestroyed)) return
                    startMain()
                }
            }
        )
    }

    private fun navigateToMain() {
        if (AppPurchase.getInstance().initBillingFinish) {
            val remainDelayTime: Long = System.currentTimeMillis() - startLoadSplashAds
            if (remainDelayTime >= ADS_DELAY) {
                executeNavigateToMain()
            } else {
                Handler(mainLooper).postDelayed(
                    { this.executeNavigateToMain() },
                    ADS_DELAY - remainDelayTime
                )
            }
        } else {
            AppPurchase.getInstance().setBillingListener({
                runOnUiThread {
                    val remainDelayTime: Long =
                        System.currentTimeMillis() - startLoadSplashAds
                    if (remainDelayTime >= ADS_DELAY) {
                        executeNavigateToMain()
                    } else {
                        Handler(mainLooper).postDelayed(
                            { this.executeNavigateToMain() },
                            ADS_DELAY - remainDelayTime
                        )
                    }
                }
            }, 2000)
        }
    }

    private fun initAdsNativeLanguage() {
        if (!AppPurchase.getInstance().isPurchased
            && SharePreferenceUtils.getFirstOpenApp(this)
//            && FirebaseUtils.getShowLanguageFirstOpen(this)
            && App.getStorageCommon()?.nativeAdLanguage?.value == null
        ) {
            AperoAd.getInstance().loadNativeAdResultCallback(
                this,
                getIdAdsNativeLanguage(),
                R.layout.native_admod_language,
                object : AperoAdCallback() {
                    override fun onNativeAdLoaded(nativeAd: ApNativeAd) {
                        super.onNativeAdLoaded(nativeAd)
                        App.getStorageCommon()?.nativeAdLanguage?.postValue(
                            nativeAd
                        )
                    }
                }
            )
        }
    }
    abstract fun getIdAdsNativeLanguage() : String

}
