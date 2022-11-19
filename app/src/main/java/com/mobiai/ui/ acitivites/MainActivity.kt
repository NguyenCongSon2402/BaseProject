package com.mobiai.ui.acitivites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.mobiai.base.lqlbasecode.util.INTENT_FILED_FILE_PATH
import com.mobiai.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    companion object {
        const val OPEN_PATH = "OPEN_PATH"

        fun startMain(activity: Context, clearTask: Boolean = false, path: String = "") {
            val intent = Intent(activity, MainActivity::class.java).apply {
                if (clearTask) {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
            }
            intent.putExtra(INTENT_FILED_FILE_PATH, path)
            activity.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}