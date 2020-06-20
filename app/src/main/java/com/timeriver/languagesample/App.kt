package com.timeriver.languagesample

import android.app.Application
import android.os.Build
import com.timeriver.languagesample.util.changeAppLanguage
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
    }

    companion object {
        lateinit var appContext: App
    }

}