package com.hololo.app.squarerepo.repo.project

import androidx.annotation.MainThread
import androidx.paging.PagedList
import com.hololo.app.squarerepo.db.AppDatabase
import com.hololo.app.squarerepo.db.entities.ProjectEntity
import com.hololo.app.squarerepo.db.views.ProjectEntityView
import com.hololo.app.squarerepo.service.GithubAPI
import com.hololo.app.squarerepo.service.response.RepoResponse
import com.hololo.app.squarerepo.utils.PagingRequestHelper
import com.hololo.app.squarerepo.utils.createStatusLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.concurrent.Executor

class ProjectBoundaryCallback(val db: AppDatabase, private val api: GithubAPI, private val ioExecutor: Executor) : PagedList.BoundaryCallback<ProjectEntityView>() {

    val helper = PagingRequestHelper(ioExecutor)
    val networkState = helper.createStatusLiveData()

    @MainThread
    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            api.getSquareRepos(1).enqueue(handleResponse(it, 1))
        }
    }

    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: ProjectEntityView) {
        Timber.e("Item at the end next page is ${itemAtEnd.project?.nextPage}")
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            api.getSquareRepos(itemAtEnd.project?.nextPage
                    ?: -1).enqueue(handleResponse(it, itemAtEnd.project?.nextPage ?: -1))
        }
    }

    private fun handleResponse(it: PagingRequestHelper.Request.Callback, page: Int): Callback<List<RepoResponse>> {
        return object : Callback<List<RepoResponse>> {
            override fun onResponse(call: Call<List<RepoResponse>>, response: Response<List<RepoResponse>>) {
                insertItemsIntoDb(response, it, page)
            }

            override fun onFailure(call: Call<List<RepoResponse>>?, t: Throwable) {
                it.recordFailure(t)
            }

        }
    }

    private fun insertItemsIntoDb(response: Response<List<RepoResponse>>?,
                                  it: PagingRequestHelper.Request.Callback, currentPage: Int) {
        ioExecutor.execute {
            response?.body()?.let { body ->

                db.runInTransaction {
                    val list = mutableListOf<ProjectEntity>()
                    body.forEach {
                        val entity = ProjectEntity(it)
                        entity.nextPage = currentPage + 1
                        list.add(entity)
                    }

                    db.projectDao().addAll(list)
                }
                it.recordSuccess()
            }
        }
    }

}