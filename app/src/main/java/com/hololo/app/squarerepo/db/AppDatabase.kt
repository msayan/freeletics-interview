package com.hololo.app.squarerepo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hololo.app.squarerepo.db.dao.BookmarkDao
import com.hololo.app.squarerepo.db.dao.ProjectDao
import com.hololo.app.squarerepo.db.dao.StargazersDao
import com.hololo.app.squarerepo.db.entities.BookmarkEntity
import com.hololo.app.squarerepo.db.entities.ProjectEntity
import com.hololo.app.squarerepo.db.entities.StargazersEntity

@Database(entities = arrayOf(ProjectEntity::class, StargazersEntity::class, BookmarkEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun projectDao(): ProjectDao
    abstract fun stargazersDao(): StargazersDao
    abstract fun bookmarkDao(): BookmarkDao
}