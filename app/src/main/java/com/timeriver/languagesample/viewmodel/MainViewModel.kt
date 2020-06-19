package com.timeriver.languagesample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope

/**
 * 明天完全掌握coroutines的概念和用法
 */
class MainViewModel : ViewModel() {

    val data = liveData {
        emit("String")
    }

    fun optNetData() {
        viewModelScope
    }

}