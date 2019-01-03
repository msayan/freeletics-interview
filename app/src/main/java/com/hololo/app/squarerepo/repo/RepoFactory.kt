package com.hololo.app.squarerepo.repo

import android.app.Application
import com.hololo.app.squarerepo.core.BaseRepo
import com.hololo.app.squarerepo.db.AppDatabase
import com.hololo.app.squarerepo.repo.project.ProjectRepo
import com.hololo.app.squarerepo.repo.stargazers.StargazersRepo
import com.hololo.app.squarerepo.service.GithubAPI
import java.util.concurrent.Executors

class RepoFactory(val api: GithubAPI, val db: AppDatabase, val app: Application) {

    inline fun <reified T : BaseRepo> createInstance(): BaseRepo = when (T::class) {
        ProjectRepo::class -> ProjectRepo(api, db, app, Executors.newSingleThreadExecutor())
        StargazersRepo::class -> StargazersRepo(api, db, app, Executors.newSingleThreadExecutor())
        else -> throw IllegalArgumentException()
    }

}