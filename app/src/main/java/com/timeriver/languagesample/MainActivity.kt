package com.timeriver.languagesample

import android.os.Bundle


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
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fl_container, LanguageFragment())
            .commitAllowingStateLoss()
    }

}
