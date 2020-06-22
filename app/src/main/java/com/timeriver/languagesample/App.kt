package com.timeriver.languagesample

import android.app.Application
import android.os.Build
import com.timeriver.languagesample.util.changeAppLanguage
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
        startKoin {
            // Android context
            androidContext(this@App)
            // modules
            modules(koinModule)
        }
        Timber.plant(Timber.DebugTree())
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            changeAppLanguage()
        }
        UMConfigure.setLogEnabled(BuildConfig.DEBUG)
        UMConfigure.init(this, UMENG_APPKEY, null, UMConfigure.DEVICE_TYPE_PHONE, null)
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO)
    }

    companion object {
        lateinit var appContext: App
        private const val UMENG_APPKEY = "5ef05a1d978eea088379b1a2"
    }

}