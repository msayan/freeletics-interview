package com.hololo.app.squarerepo.db.dao

import androidx.room.Room
import com.hololo.app.squarerepo.db.AppDatabase
import com.hololo.app.squarerepo.db.entities.StargazersEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.util.*

@RunWith(RobolectricTestRunner::class)
class StargazersDaoTest {
    private lateinit var db: AppDatabase
    private val REPO_NAME = "test-repo"

    @Before
    fun initDb() {
        db = Room.inMemoryDatabaseBuilder(
                RuntimeEnvironment.application.baseContext,
                AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertData() {
        db.stargazersDao().insertStargazer(StargazersEntity())
        val count = db.stargazersDao().getCount(REPO_NAME)

        assert(count > 0)
    }

    @Test
    fun insertBulkData() {
        val list = mutableListOf<StargazersEntity>()
        for (i in 0..10) {
            list.add(createStargazersEntity())
        }
        db.stargazersDao().addAll(list)

        val dbCount = db.stargazersDao().getCount(REPO_NAME)

        assert(dbCount == list.size)
    }

    private fun createStargazersEntity(): StargazersEntity {
        val id = Random().nextInt()
        val entity = StargazersEntity()
        entity.githubId = id.toLong()
        entity.repoName = REPO_NAME

        return entity
    }
}