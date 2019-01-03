package com.hololo.app.squarerepo.di.module

import com.hololo.app.squarerepo.App
import com.hololo.app.squarerepo.db.AppDatabase
import com.hololo.app.squarerepo.repo.RepoFactory
import com.hololo.app.squarerepo.service.GithubAPI
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class RepoModule() {

    @Provides
    @Singleton
    fun provideFactory(api: GithubAPI, db: AppDatabase, app: App): RepoFactory {
        return RepoFactory(api, db, app)
    }
}