package com.timeriver.languagesample.ui.activity

import android.os.Bundle
import com.timeriver.languagesample.BaseActivity
import com.timeriver.languagesample.R
import com.timeriver.languagesample.ui.adapter.MainPageAdapter
import com.timeriver.languagesample.ui.fragment.CoroutinesFragment
import com.timeriver.languagesample.ui.fragment.LanguageFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import timber.log.Timber

/**
 * Switch Language within app sample
 *
 * en zh-rCn zh-rTW
 * 小米手机MIUI中繁体中文对应的是zh-rTW，所以这里使用zh-rTW代表繁体中文
 */
class MainActivity : BaseActivity() {

    //the best practice for fragment
    //直接通过Koin通过无参构造注入fragment实例，数据通过ViewModel传递
    private val languageFragment: LanguageFragment by inject()

    private val coroutinesFragment: CoroutinesFragment by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main_pager.adapter = MainPageAdapter(
            activity = this,
            list = listOf(coroutinesFragment, languageFragment)
        )
    }

    override fun onBackPressed() {
        //super.onBackPressed() 时，将调用 addCallback 注册的所有回调。
        super.onBackPressed()
        Timber.d("MainActivity, onBackPressed")
    }

}
