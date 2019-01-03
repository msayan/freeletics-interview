package com.hololo.app.squarerepo.service

import com.hololo.app.squarerepo.core.Constants
import com.hololo.app.squarerepo.service.response.RepoResponse
import com.hololo.app.squarerepo.service.response.StargazersResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubAPI {

    @GET("/orgs/square/repos?per_page=${Constants.PAGE_SIZE}")
    fun getSquareRepos(@Query("page") pageIndex: Int): Call<List<RepoResponse>>

    @GET("/repos/square/{repositoryId}/stargazers?per_page=${Constants.PAGE_SIZE}")
    fun getStargazers(@Path("repositoryId") repositoryId: String, @Query("page") pageIndex: Int): Call<List<StargazersResponse>>

}