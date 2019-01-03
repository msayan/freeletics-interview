package com.hololo.app.squarerepo.repo.stargazers

import androidx.annotation.MainThread
import androidx.paging.PagedList
import com.hololo.app.squarerepo.db.AppDatabase
import com.hololo.app.squarerepo.db.entities.StargazersEntity
import com.hololo.app.squarerepo.service.GithubAPI
import com.hololo.app.squarerepo.service.response.StargazersResponse
import com.hololo.app.squarerepo.utils.PagingRequestHelper
import com.hololo.app.squarerepo.utils.createStatusLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.concurrent.Executor

class StargazersBoundaryCallback(val db: AppDatabase, private val api: GithubAPI, private val ioExecutor: Executor, private val repositoryId: String) : PagedList.BoundaryCallback<StargazersEntity>() {

    val helper = PagingRequestHelper(ioExecutor)
    val networkState = helper.createStatusLiveData()

    @MainThread
    override fun onZeroItemsLoaded() {
        helper.runIfNotRunning(PagingRequestHelper.RequestType.INITIAL) {
            api.getStargazers(repositoryId, 1).enqueue(handleResponse(it, 1))
        }
    }

    @MainThread
    override fun onItemAtEndLoaded(itemAtEnd: StargazersEntity) {
        Timber.e("Item at the end next page is ${itemAtEnd.nextPage}")
        helper.runIfNotRunning(PagingRequestHelper.RequestType.AFTER) {
            api.getStargazers(repositoryId, itemAtEnd.nextPage).enqueue(handleResponse(it, itemAtEnd.nextPage))
        }
    }

    private fun handleResponse(it: PagingRequestHelper.Request.Callback, page: Int): Callback<List<StargazersResponse>> {
        return object : Callback<List<StargazersResponse>> {
            override fun onResponse(call: Call<List<StargazersResponse>>, response: Response<List<StargazersResponse>>) {
                insertItemsIntoDb(response, it, page)
            }

            override fun onFailure(call: Call<List<StargazersResponse>>?, t: Throwable) {
                it.recordFailure(t)
            }

        }
    }

    private fun insertItemsIntoDb(response: Response<List<StargazersResponse>>?,
                                  it: PagingRequestHelper.Request.Callback, currentPage: Int) {
        ioExecutor.execute {
            val list = mutableListOf<StargazersEntity>()
            response?.body()?.let { body ->
                body.forEach {
                    val entity = StargazersEntity(it, repositoryId)
                    entity.nextPage = currentPage + 1
                    list.add(entity)
                }

                db.runInTransaction {
                    db.stargazersDao().addAll(list)
                }
                it.recordSuccess()
            }
        }
    }

}