package com.hbacakk.imagefilter.di

import com.hbacakk.imagefilter.repositories.EditImageRepository
import com.hbacakk.imagefilter.repositories.EditImageRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule= module {
    factory<EditImageRepository>{EditImageRepositoryImpl(androidContext())}
}