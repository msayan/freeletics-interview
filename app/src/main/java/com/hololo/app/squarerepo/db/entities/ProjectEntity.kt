package com.hololo.app.squarerepo.db.entities

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.hololo.app.squarerepo.core.BaseEntity
import com.hololo.app.squarerepo.service.response.RepoResponse


@Entity(indices = arrayOf(Index("githubId", unique = true)))
class ProjectEntity() : BaseEntity(), Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
    var githubId: Long = 0
    var stargazersCount: Int? = null
    var fullName: String? = null
    var description: String? = null
    var nextPage: Int = 1

    constructor(parcel: Parcel) : this() {
        id = parcel.readLong()
        githubId = parcel.readLong()
        stargazersCount = parcel.readValue(Int::class.java.classLoader) as? Int
        fullName = parcel.readString()
        description = parcel.readString()
        nextPage = parcel.readInt()
    }

    constructor(response: RepoResponse) : this() {
        githubId = response.id?.toLong() ?: -1L
        stargazersCount = response.stargazersCount
        fullName = response.name
        description = response.description
    }

    fun getStarGazers(): String {
        return "$stargazersCount"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeLong(githubId)
        parcel.writeValue(stargazersCount)
        parcel.writeString(fullName)
        parcel.writeString(description)
        parcel.writeInt(nextPage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProjectEntity> {
        override fun createFromParcel(parcel: Parcel): ProjectEntity {
            return ProjectEntity(parcel)
        }

        override fun newArray(size: Int): Array<ProjectEntity?> {
            return arrayOfNulls(size)
        }
    }
}