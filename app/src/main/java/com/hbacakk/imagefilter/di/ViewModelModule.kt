package com.hbacakk.imagefilter.di

import com.hbacakk.imagefilter.viewmodels.EditImageViewModels
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule= module {
    viewModel{EditImageViewModels(editImageRepository = get ())}
}