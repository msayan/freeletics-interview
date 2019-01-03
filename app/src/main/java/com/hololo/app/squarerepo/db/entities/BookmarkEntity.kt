package com.hololo.app.squarerepo.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(indices = arrayOf(Index("githubId", unique = true)))
class BookmarkEntity{
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var githubId: Long = 0

    constructor(githubId: Long) {
        this.githubId = githubId
    }
}