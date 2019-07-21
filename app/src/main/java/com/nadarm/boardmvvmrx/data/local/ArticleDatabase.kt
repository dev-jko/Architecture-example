package com.nadarm.boardmvvmrx.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nadarm.boardmvvmrx.data.model.ArticleData

@Database(entities = [ArticleData::class], version = 1)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    companion object {
        private var INSTANCE: ArticleDatabase? = null

        fun getInstance(context: Context): ArticleDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): ArticleDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                ArticleDatabase::class.java, "board.db"
            )
                .build()
        }

    }


}