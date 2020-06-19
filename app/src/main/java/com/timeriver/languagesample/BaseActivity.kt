package com.timeriver.languagesample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import com.timeriver.languagesample.util.attachLanguageInfo

abstract class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.attachLanguageInfo())
    }

}