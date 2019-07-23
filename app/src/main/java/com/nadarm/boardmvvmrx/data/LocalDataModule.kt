package com.nadarm.boardmvvmrx.data

import android.app.Application
import com.nadarm.boardmvvmrx.data.local.ArticleDao
import com.nadarm.boardmvvmrx.data.local.ArticleDatabase
import com.nadarm.boardmvvmrx.data.model.mapper.ArticleLocalMapper
import com.nadarm.boardmvvmrx.data.remote.ArticleRemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalDataModule {

    @Singleton
    @Provides
    fun providesArticleLocalMapper(): ArticleLocalMapper {
        return ArticleLocalMapper
    }

    @Singleton
    @Provides
    fun providesArticleDatabase(application: Application): ArticleDatabase {
        return ArticleDatabase.getInstance(application)
    }

    @Singleton
    @Provides
    fun providesArticleDao(articleDatabase: ArticleDatabase): ArticleDao {
        return articleDatabase.articleDao()
    }

    @Singleton
    @Provides
    fun providesArticleRemoteDataSource(): ArticleRemoteDataSource {
        return ArticleRemoteDataSource
    }

}