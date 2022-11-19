package com.mobiai.ui.storage

import androidx.lifecycle.MutableLiveData
import com.ads.control.ads.wrapper.ApInterstitialAd
import com.ads.control.ads.wrapper.ApNativeAd

class StorageCommon {
    var nativeAdLanguage = MutableLiveData<ApNativeAd>()
    private var interstitialAdClickFolder: ApInterstitialAd? = null
    private var interstitialViewFile: ApInterstitialAd? = null

    open fun getInterstitialAdClickFolder(): ApInterstitialAd? {
        return interstitialAdClickFolder
    }

    open fun setInterstitialAdClickFolder(interstitialAdViewTemplate: ApInterstitialAd?) {
        this.interstitialAdClickFolder = interstitialAdViewTemplate
    }

    open fun interstitialAdClickFolderReady(): Boolean {
        return interstitialAdClickFolder != null && interstitialAdClickFolder!!.isReady
    }

    open fun getInterstitialViewFile(): ApInterstitialAd? {
        return interstitialViewFile
    }

    open fun setInterstitialViewFile(interstitialAdExportVideo: ApInterstitialAd?) {
        this.interstitialViewFile = interstitialAdExportVideo
    }

    open fun interAdViewFileReady(): Boolean {
        return interstitialViewFile != null && interstitialViewFile!!.isReady
    }
}