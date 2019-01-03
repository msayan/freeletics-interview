package com.hololo.app.squarerepo

import com.facebook.stetho.Stetho
import com.hololo.app.squarerepo.di.component.DaggerApplicationComponent
import com.hololo.app.squarerepo.di.module.ApplicationModule
import com.hololo.app.squarerepo.di.module.DatabaseModule
import com.hololo.app.squarerepo.di.module.NetModule
import com.hololo.app.squarerepo.di.module.RepoModule
import timber.log.Timber

class App : android.app.Application() {

    val component by lazy {
        DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .databaseModule(DatabaseModule())
                .netModule(NetModule())
                .repoModule(RepoModule())
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)

        Timber.plant(Timber.DebugTree())
    }
}

