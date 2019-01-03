package com.hololo.app.squarerepo.ui.list

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.hololo.app.squarerepo.App
import com.hololo.app.squarerepo.core.BaseViewModel
import com.hololo.app.squarerepo.db.entities.ProjectEntity
import com.hololo.app.squarerepo.db.views.ProjectEntityView
import com.hololo.app.squarerepo.repo.RepoFactory
import com.hololo.app.squarerepo.repo.project.ProjectRepo
import javax.inject.Inject

class ListFragmentViewModel(app: Application) : BaseViewModel(app) {

    @Inject
    lateinit var repoFactory: RepoFactory

    private var repo: ProjectRepo

    init {
        (app as? App)?.component?.inject(this)

        repo = repoFactory.createInstance<ProjectRepo>() as ProjectRepo
    }

    fun getProjects(): LiveData<PagedList<ProjectEntityView>> {
        return repo.getProjects().pagedList
    }


}