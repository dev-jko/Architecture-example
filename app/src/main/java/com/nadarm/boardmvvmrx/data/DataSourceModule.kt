package com.nadarm.boardmvvmrx.data

import android.app.Application
import com.nadarm.boardmvvmrx.BuildConfig
import com.nadarm.boardmvvmrx.R
import com.nadarm.boardmvvmrx.data.local.ArticleDao
import com.nadarm.boardmvvmrx.data.local.ArticleDatabase
import com.nadarm.boardmvvmrx.data.model.mapper.ArticleDataMapper
import com.nadarm.boardmvvmrx.data.remote.ArticleService
import com.nadarm.boardmvvmrx.data.remote.FakeArticleService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataSourceModule {

    @Singleton
    @Provides
    fun provideArticleDataMapper(): ArticleDataMapper {
        return ArticleDataMapper
    }

    @Singleton
    @Provides
    fun provideArticleDatabase(application: Application): ArticleDatabase {
        return ArticleDatabase.getInstance(application)
    }

    @Singleton
    @Provides
    fun provideArticleDao(articleDatabase: ArticleDatabase): ArticleDao {
        return articleDatabase.articleDao()
    }

    @Singleton
    @Provides
    fun provideArticleService(application: Application): ArticleService {
        if (BuildConfig.DEBUG) {
            return FakeArticleService()
        }

        val baseUrl = application.getString(R.string.ApiBaseUrl)
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(ArticleService::class.java)
    }

}