package com.timeriver.languagesample.domain.model

import java.util.*

enum class LanguageType(
    val locale: Locale,
    val localName: String
) {
    ENGLISH(Locale.ENGLISH, "en"),
    SIMPLIFIED_CHINESE(Locale.SIMPLIFIED_CHINESE, "zh"),
    TRADITIONAL_CHINESE(Locale.TRADITIONAL_CHINESE, "zh-Hant"),
}