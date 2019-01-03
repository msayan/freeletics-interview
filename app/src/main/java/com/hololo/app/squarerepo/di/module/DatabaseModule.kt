package com.hololo.app.squarerepo.di.module

import androidx.room.Room
import android.content.Context
import com.hololo.app.squarerepo.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun getDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context,
                AppDatabase::class.java, "square-repo-db").build()
    }

}