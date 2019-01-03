package com.hololo.app.squarerepo.db.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.hololo.app.squarerepo.db.entities.StargazersEntity

@Dao
interface StargazersDao {

    @Query("SELECT * FROM StargazersEntity")
    fun getStargazers(): LiveData<List<StargazersEntity>>

    @Query("SELECT * FROM StargazersEntity where id = :repoId")
    fun getStargazer(repoId: Long): StargazersEntity

    @Query("SELECT * FROM StargazersEntity where repoName = :repoName order by id asc")
    fun getPagedStargazers(repoName: String): DataSource.Factory<Int, StargazersEntity>

    @Insert(onConflict = REPLACE)
    fun insertStargazer(stargazerEntity: StargazersEntity)

    @Insert(onConflict = REPLACE)
    fun addAll(stargazerEntities: List<StargazersEntity>)

    @Update
    fun updateStargazer(stargazerEntity: StargazersEntity)

    @Delete
    fun deleteStargazer(stargazerEntity: StargazersEntity)

    @Query("Select count(*) from StargazersEntity where repoName = :repoName")
    fun getCount(repoName: String): Int

    @Query("delete from StargazersEntity where id > -1")
    fun clear()

}