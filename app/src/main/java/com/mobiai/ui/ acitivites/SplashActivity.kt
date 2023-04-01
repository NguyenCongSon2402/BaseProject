package com.mobiai.ui

import android.annotation.SuppressLint
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.ads.control.admob.Admob
import com.ads.control.ads.AperoAd
import com.ads.control.ads.AperoAdCallback
import com.ads.control.ads.wrapper.ApAdError
import com.mobiai.BuildConfig
import com.mobiai.R
import com.mobiai.base.lqlbasecode.ui.BaseSplashActivity
import com.mobiai.base.lqlbasecode.util.SharePreferenceUtils
import com.mobiai.ui.acitivites.MainActivity
import com.mobiai.ui.acitivites.OnboardingActivity
import com.mobiai.ui.activities.LanguageActivity

class SplashActivity : BaseSplashActivity() {
    override fun getIdAdsSplash(): String {
        return BuildConfig.inter_splash
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Admob.getInstance().setOpenActivityAfterShowInterAds(false)
        loadAdsSplash()
    }

    override fun getIdAdsNativeLanguage(): String {
        return BuildConfig.native_language
    }

    override fun startMain() {
        if (SharePreferenceUtils.getLanguage(this) == null) {
            LanguageActivity.start(this)}
//        } else if (!SharePreferenceUtils.getCompleteOnBoarding(this)) {
//            OnboardingActivity.start(this)
//        }
        else {
            showNextScreen() //main or anything?
        }
        finish()
    }

    override fun onDestroy() {
        releaseWakeUp()
        super.onDestroy()
    }


    private fun showNextScreen() {
        MainActivity.startMain(this, true)
    }

    override fun onResume() {
        super.onResume()
        AperoAd.getInstance().onCheckShowSplashWhenFail(
            this,
            object : AperoAdCallback() {
                override fun onNextAction() {
                    super.onNextAction()
                    if (isDestroyed || isOnStop || isFinishing) return
                    startMain()
                }

                override fun onAdFailedToShow(adError: ApAdError?) {
                    super.onAdFailedToShow(adError)
                    if (isDestroyed || isOnStop || isFinishing) return
                    startMain()
                }
            },
            1000
        )
    }

    override fun onStop() {
        super.onStop()
        isOnStop = true
    }

    override fun onStart() {
        super.onStart()
        isOnStop = false
    }
}