package com.hololo.app.squarerepo.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.hololo.app.squarerepo.core.BaseEntity
import com.hololo.app.squarerepo.service.response.StargazersResponse

@Entity(indices = arrayOf(Index("githubId", unique = true)))
class StargazersEntity() : BaseEntity() {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var githubId: Long = 0
    var username: String? = null
    var image: String? = null
    var repoName: String? = null
    var nextPage: Int = 1

    constructor(data: StargazersResponse, repo: String) : this() {
        githubId = data.id?.toLong() ?: -1L
        username = data.login
        image = data.avatarUrl
        repoName = repo
    }
}