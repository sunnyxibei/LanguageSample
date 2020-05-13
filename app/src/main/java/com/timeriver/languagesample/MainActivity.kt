package com.timeriver.languagesample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * Switch Language within app sample
 *
 * en zh-rCn zh-rSG
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("MainActivity", Locale.getDefault().displayName)

        button_en.setOnClickListener {
            //todo 切换英文
        }
        button_zh_cn.setOnClickListener {
            //todo 切换中文
        }
        button_zh_tw.setOnClickListener {
            //todo 切换中文繁体
        }
    }
}
