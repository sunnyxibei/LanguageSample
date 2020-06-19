package com.timeriver.languagesample

import com.timeriver.languagesample.viewmodel.MainViewModel
import org.koin.androidx.experimental.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

val koinModule = module {

    viewModel { MainViewModel() }

}