package com.timeriver.languagesample

import android.app.Application
import android.os.Build
import timber.log.Timber


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
        Timber.plant(Timber.DebugTree())
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            changeAppLanguage()
        }
    }

    companion object {
        lateinit var appContext: App
    }

}