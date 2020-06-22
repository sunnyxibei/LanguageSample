package com.timeriver.languagesample

import com.timeriver.languagesample.ui.fragment.CoroutinesFragment
import com.timeriver.languagesample.ui.fragment.LanguageFragment
import com.timeriver.languagesample.viewmodel.MainViewModel
import org.koin.androidx.fragment.dsl.fragment
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val koinModule = module {

    fragment { CoroutinesFragment() }

    fragment { LanguageFragment() }

    viewModel { MainViewModel() }

}