package com.hololo.app.squarerepo.db.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update
import com.hololo.app.squarerepo.db.entities.BookmarkEntity

@Dao
interface BookmarkDao {

    @Query("SELECT * FROM BookmarkEntity")
    fun getBookmarks(): LiveData<List<BookmarkEntity>>

    @Query("SELECT * FROM BookmarkEntity where id = :repoId")
    fun getBookmark(repoId: Long): BookmarkEntity

    @Query("SELECT * FROM BookmarkEntity order by id asc")
    fun getPagedBookmarks(): DataSource.Factory<Int, BookmarkEntity>

    @Query("Select Count(*) FROM BookmarkEntity where githubId = :repoId")
    fun getBookmarkCount(repoId: Long): LiveData<Int>

    @Insert(onConflict = REPLACE)
    fun insertBookmark(bookmarkEntity: BookmarkEntity)

    @Insert(onConflict = REPLACE)
    fun addAll(bookmarkEntities: List<BookmarkEntity>)

    @Update
    fun updateBookmark(bookmarkEntity: BookmarkEntity)

    @Query("Delete from BookmarkEntity  where githubId = :repoId ")
    fun deleteBookmark(repoId: Long)

    @Query("Select count(*) from BookmarkEntity")
    fun getCount(): Int

    @Query("delete from BookmarkEntity where id > -1")
    fun clear()

}