package com.hololo.app.squarerepo.db.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.hololo.app.squarerepo.db.entities.ProjectEntity
import com.hololo.app.squarerepo.db.views.ProjectEntityView

@Dao
interface ProjectDao {

    @Query("SELECT * FROM ProjectEntity")
    fun getProjects(): LiveData<List<ProjectEntity>>

    @Query("SELECT * FROM ProjectEntity where id = :repoId")
    fun getProject(repoId: Long): ProjectEntity

    @Query("SELECT * FROM ProjectEntity order by id asc")
    fun getPagedProjects(): DataSource.Factory<Int, ProjectEntityView>

    @Query("SELECT * FROM ProjectEntity where githubId in (Select githubId FROM BookmarkEntity)")
    fun getBookmarks(): LiveData<List<ProjectEntity>>

    @Insert(onConflict = REPLACE)
    fun insertProject(projectEntity: ProjectEntity)

    @Insert(onConflict = REPLACE)
    fun addAll(projectEntities: List<ProjectEntity>)

    @Update
    fun updateProject(projectEntity: ProjectEntity)

    @Delete
    fun deleteProject(projectEntity: ProjectEntity)

    @Query("Select count(*) from ProjectEntity")
    fun getCount(): Int

    @Query("delete from ProjectEntity where id > -1")
    fun clear()

}