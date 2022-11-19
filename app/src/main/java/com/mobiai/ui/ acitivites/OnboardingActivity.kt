package com.mobiai.ui.acitivites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.mobiai.base.lqlbasecode.ui.onboarding.BaseOnboardingActivity

class OnboardingActivity : BaseOnboardingActivity() {
    companion object {
        private const val INTENT_DATA = "intent_data"
        fun start(context: Context, path: String? = null) {
            Intent(context, OnboardingActivity::class.java).apply {
                putExtra(INTENT_DATA, path)
                context.startActivity(this)
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}