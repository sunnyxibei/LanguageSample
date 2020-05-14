package com.timeriver.languagesample

import android.app.Application
import android.os.Build


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = this
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            changeAppLanguage()
        }
    }

    companion object {
        lateinit var appContext: App
    }

}