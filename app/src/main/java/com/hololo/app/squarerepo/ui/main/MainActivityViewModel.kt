package com.hololo.app.squarerepo.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.hololo.app.squarerepo.App
import com.hololo.app.squarerepo.core.BaseViewModel
import com.hololo.app.squarerepo.db.entities.ProjectEntity
import com.hololo.app.squarerepo.repo.RepoFactory
import com.hololo.app.squarerepo.repo.project.ProjectRepo
import javax.inject.Inject

class MainActivityViewModel(app: Application) : BaseViewModel(app) {

    init {
        (app as? App)?.component?.inject(this)
    }

}