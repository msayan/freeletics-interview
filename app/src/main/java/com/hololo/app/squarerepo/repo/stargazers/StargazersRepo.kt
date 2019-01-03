package com.hololo.app.squarerepo.repo.stargazers

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.hololo.app.squarerepo.core.BaseRepo
import com.hololo.app.squarerepo.core.Constants
import com.hololo.app.squarerepo.core.Listing
import com.hololo.app.squarerepo.db.AppDatabase
import com.hololo.app.squarerepo.db.entities.StargazersEntity
import com.hololo.app.squarerepo.service.GithubAPI
import java.util.concurrent.Executor

class StargazersRepo(api: GithubAPI, db: AppDatabase, app: Application, private val executor: Executor) : BaseRepo(api, db, app) {

    fun getStargazers(repositoryId: String): Listing<StargazersEntity> {

        val boundaryCallback = StargazersBoundaryCallback(
                db,
                api,
                executor,
                repositoryId
        )

        val dataSourceFactory = db.stargazersDao().getPagedStargazers(repositoryId)
        val config = PagedList.Config.Builder()
                .setPageSize(Constants.PAGE_SIZE)
                .build()
        val builder = LivePagedListBuilder(dataSourceFactory, config)
                .setBoundaryCallback(boundaryCallback)


        boundaryCallback.onZeroItemsLoaded()
        return Listing(
                pagedList = builder.build(),
                networkState = boundaryCallback.networkState,
                retry = {
                    boundaryCallback.helper.retryAllFailed()
                },
                loadingState = MutableLiveData<Boolean>()
        )
    }

}