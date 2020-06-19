package com.timeriver.languagesample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope

class MainViewModel : ViewModel() {

    val data = liveData {
        emit("String")
    }

    fun optNetData() {
        viewModelScope
    }

}