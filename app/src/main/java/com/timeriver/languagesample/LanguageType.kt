package com.timeriver.languagesample

import java.util.*

enum class LanguageType(
    val locale: Locale,
    val localName: String
) {
    ENGLISH(Locale.ENGLISH, "en"),
    SIMPLIFIED_CHINESE(Locale.SIMPLIFIED_CHINESE, "zh"),
    TRADITIONAL_CHINESE(Locale.TRADITIONAL_CHINESE, "zh-Hant"),
}