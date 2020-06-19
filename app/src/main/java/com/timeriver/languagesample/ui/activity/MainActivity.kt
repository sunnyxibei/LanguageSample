package com.timeriver.languagesample.ui.activity

import android.os.Bundle
import com.timeriver.languagesample.BaseActivity
import com.timeriver.languagesample.R
import com.timeriver.languagesample.ui.adapter.MainPageAdapter
import com.timeriver.languagesample.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 * Switch Language within app sample
 *
 * en zh-rCn zh-rTW
 * 小米手机MIUI中繁体中文对应的是zh-rTW，所以这里使用zh-rTW代表繁体中文
 */
class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main_pager.adapter = MainPageAdapter(this)
    }

    override fun onBackPressed() {
        //super.onBackPressed() 时，将调用 addCallback 注册的所有回调。
        super.onBackPressed()
        Timber.d("MainActivity, onBackPressed")
    }

}
