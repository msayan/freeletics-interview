package com.hololo.app.squarerepo.core

import android.app.Application
import com.hololo.app.squarerepo.db.AppDatabase
import com.hololo.app.squarerepo.service.GithubAPI

open class BaseRepo(val api: GithubAPI, val db: AppDatabase, val app: Application)