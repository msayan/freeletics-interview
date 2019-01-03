package com.hololo.app.squarerepo.db.dao

import androidx.room.Room
import com.hololo.app.squarerepo.db.AppDatabase
import com.hololo.app.squarerepo.db.entities.ProjectEntity
import com.hololo.app.squarerepo.db.entities.ProjectEntityTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import java.util.*

@RunWith(RobolectricTestRunner::class)
class ProjectDaoTest {

    private lateinit var db: AppDatabase

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
        db.projectDao().insertProject(createProjectEntity())
        val count = db.projectDao().getCount()

        assert(count > 0)
    }

    @Test
    fun insertBulkData() {
        val list = mutableListOf<ProjectEntity>()
        for (i in 0..10) {
            list.add(createProjectEntity())
        }
        db.projectDao().addAll(list)

        val dbCount = db.projectDao().getCount()

        assert(dbCount == list.size)
    }

    private fun createProjectEntity(): ProjectEntity {
        val id = Random().nextInt()
        val entity = ProjectEntity()
        entity.githubId = id.toLong()
        entity.fullName = "Test Entity $id"

        return entity
    }
}