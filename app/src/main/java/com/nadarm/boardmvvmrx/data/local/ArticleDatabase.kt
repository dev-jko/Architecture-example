package com.nadarm.boardmvvmrx.data.local

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nadarm.boardmvvmrx.data.model.ArticleData

@Database(entities = [ArticleData::class], version = 2)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    companion object {
        private var INSTANCE: ArticleDatabase? = null

        fun getInstance(application: Application): ArticleDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(application).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(application: Application): ArticleDatabase {
            return Room.databaseBuilder(
                application.applicationContext,
                ArticleDatabase::class.java, "board.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }

    }


}