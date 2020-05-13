package com.timeriver.languagesample

import android.annotation.TargetApi
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import android.util.Log
import java.util.*

@Suppress("DEPRECATION")
fun Context.changeAppLanguage() {
    val configuration = resources.configuration
    configuration.setLocale(getLocaleByLanguage())
    resources.updateConfiguration(configuration, resources.displayMetrics)
}

fun Context.attachLanguageInfo(): Context? =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        updateResources()
    } else {
        this
    }

@TargetApi(Build.VERSION_CODES.N)
fun Context.updateResources(): Context? {
    val locale: Locale = getLocaleByLanguage()
    val configuration: Configuration = resources.configuration
    configuration.setLocale(locale)
    configuration.setLocales(LocaleList(locale))
    return createConfigurationContext(configuration)
}

/**
 * 默认语言文英文，不再跟随系统语言
 */
private fun getLocaleByLanguage(): Locale =
    when (SharedPreferencesService.instance.language) {
        LanguageType.SIMPLIFIED_CHINESE.localName -> LanguageType.SIMPLIFIED_CHINESE.locale
        LanguageType.TRADITIONAL_CHINESE.localName -> LanguageType.TRADITIONAL_CHINESE.locale
        else -> LanguageType.ENGLISH.locale
    }