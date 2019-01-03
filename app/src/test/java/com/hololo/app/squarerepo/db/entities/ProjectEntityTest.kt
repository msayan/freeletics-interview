package com.hololo.app.squarerepo.db.entities

import com.hololo.app.squarerepo.service.response.RepoResponse
import org.hamcrest.Matchers.equalTo
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ErrorCollector
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*

@RunWith(JUnit4::class)
class ProjectEntityTest {

    @get:Rule
    val collector = ErrorCollector()

    @Test
    fun mapResponseToEntity() {
        val response = getRepoResponse()

        val entity = ProjectEntity(response)

        collector.checkThat(response.id?.toLong(), equalTo(entity.githubId))
        collector.checkThat(response.stargazersCount, equalTo(entity.stargazersCount))
        collector.checkThat(response.name, equalTo(entity.fullName))
        collector.checkThat(response.description, equalTo(entity.description))
    }

    private fun getRepoResponse(): RepoResponse {
        val result = RepoResponse()

        result.id = Random().nextInt()
        result.stargazersCount = Random().nextInt()
        result.name = "Repo - ${Random().nextInt()}"
        result.description = "Repo description ${Random().nextInt()}"

        return result
    }
}