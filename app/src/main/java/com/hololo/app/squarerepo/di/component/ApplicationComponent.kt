package com.hololo.app.squarerepo.di.component

import android.content.Context
import android.content.SharedPreferences
import com.hololo.app.squarerepo.App
import com.hololo.app.squarerepo.di.module.ApplicationModule
import com.hololo.app.squarerepo.di.module.DatabaseModule
import com.hololo.app.squarerepo.di.module.NetModule
import com.hololo.app.squarerepo.di.module.RepoModule
import com.hololo.app.squarerepo.ui.detail.DetailActivityViewModel
import com.hololo.app.squarerepo.ui.list.ListFragmentViewModel
import com.hololo.app.squarerepo.ui.main.MainActivityViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton

@Component(modules = arrayOf(ApplicationModule::class, NetModule::class, DatabaseModule::class, RepoModule::class))


interface ApplicationComponent {
    fun app(): App


    fun context(): Context

    fun preferences(): SharedPreferences

    fun inject(mainActivityViewModel: MainActivityViewModel)

    fun inject(mainActivityViewModel: DetailActivityViewModel)

    fun inject(listFragmentViewModel: ListFragmentViewModel)
}
