package com.hololo.app.squarerepo.db.views

import androidx.room.Embedded
import androidx.room.Relation
import com.hololo.app.squarerepo.core.BaseEntity
import com.hololo.app.squarerepo.db.entities.BookmarkEntity
import com.hololo.app.squarerepo.db.entities.ProjectEntity

class ProjectEntityView : BaseEntity() {
    @Embedded
    var project: ProjectEntity? = null
    @Relation(entityColumn = "githubId", parentColumn = "githubId", entity = BookmarkEntity::class)
    var bookmark: List<BookmarkEntity>? = null

    fun isBookmarked(): Boolean {
        return bookmark?.size ?: 0 > 0
    }
}