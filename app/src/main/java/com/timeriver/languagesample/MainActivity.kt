package com.timeriver.languagesample

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


/**
 * Switch Language within app sample
 *
 * en zh-rCn zh-rTW
 * 小米手机MIUI中繁体中文对应的是zh-rTW，所以这里使用zh-rTW代表繁体中文
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("MainActivity", Locale.getDefault().displayName)

        button_en.setOnClickListener {
            changeLanguage(LanguageType.ENGLISH.localName)
        }
        button_zh_cn.setOnClickListener {
            changeLanguage(LanguageType.SIMPLIFIED_CHINESE.localName)
        }
        button_zh_tw.setOnClickListener {
            changeLanguage(LanguageType.TRADITIONAL_CHINESE.localName)
        }
        button_reset.setOnClickListener {
            changeLanguage("")
        }
    }

    private fun changeLanguage(language: String) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            changeAppLanguage()
        }
        SharedPreferencesService.instance.language = language
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}
