package com.mobiai.base.lqlbasecode.ui

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.PowerManager
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.mobiai.base.lqlbasecode.ultility.UriUtils
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

abstract class BaseSplashActivity : AppCompatActivity() {

    private var screenLock: PowerManager.WakeLock? = null

    @SuppressLint("InvalidWakeLockTag")
    protected fun wakeUpScreen() {
        screenLock =
            (getSystemService(POWER_SERVICE) as PowerManager).newWakeLock(
                PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP,
                "mywakelocktag")

        screenLock?.acquire(3*60*1000L )
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

    protected fun getPathFromData(): String?{
        return try {
            UriUtils.getPathFromUriTryMyBest(this, intent.data)
        } catch (e: Exception) {
            null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getKeyHash()
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

}
