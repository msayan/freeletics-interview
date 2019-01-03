package com.hololo.app.squarerepo.ui.detail

import android.app.Application
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.hololo.app.squarerepo.App
import com.hololo.app.squarerepo.core.BaseViewModel
import com.hololo.app.squarerepo.db.AppDatabase
import com.hololo.app.squarerepo.db.entities.BookmarkEntity
import com.hololo.app.squarerepo.db.entities.ProjectEntity
import com.hololo.app.squarerepo.db.entities.StargazersEntity
import com.hololo.app.squarerepo.repo.RepoFactory
import com.hololo.app.squarerepo.repo.stargazers.StargazersRepo
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import javax.inject.Inject

class DetailActivityViewModel(app: Application) : BaseViewModel(app) {

    val item = ObservableField<ProjectEntity>()

    @Inject
    lateinit var repoFactory: RepoFactory

    @Inject
    lateinit var db: AppDatabase

    val repo: StargazersRepo

    init {
        (app as? App)?.component?.inject(this)
        repo = repoFactory.createInstance<StargazersRepo>() as StargazersRepo
    }

    fun getStargazers(repositoryId: String): LiveData<PagedList<StargazersEntity>> {
        return repo.getStargazers(repositoryId).pagedList
    }

    fun getBookmarkStatus(repositoryId: Long): LiveData<Int> {
        return db.bookmarkDao().getBookmarkCount(repositoryId)
    }

    fun addBookmark(githubId: Long) {
        doAsync {
            db.bookmarkDao().insertBookmark(BookmarkEntity(githubId))
            uiThread {
                Toast.makeText(getApplication(),"Added to bookmark.",Toast.LENGTH_LONG).show()
            }
        }
    }

    fun removeBookmark(githubId: Long) {
        doAsync {
            db.bookmarkDao().deleteBookmark(githubId)
            uiThread {
                Toast.makeText(getApplication(),"Removed from bookmark.",Toast.LENGTH_LONG).show()
            }
        }
    }
}