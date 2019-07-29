package com.nadarm.boardmvvmrx.data

import com.nadarm.boardmvvmrx.data.cache.ArticleCacheDataSource
import com.nadarm.boardmvvmrx.data.local.ArticleLocalDataSource
import com.nadarm.boardmvvmrx.data.remote.ArticleRemoteDataSource
import com.nadarm.boardmvvmrx.domain.repository.ArticleRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindArticleRepository(articleDataRepository: ArticleDataRepository): ArticleRepository

    @Binds
    @Singleton
    abstract fun bindArticleLocalDataSource(articleLocalDataSource: ArticleLocalDataSource): ArticleDataSource.Local

    @Binds
    @Singleton
    abstract fun bindArticleRemoteDataSource(articleRemoteDataSource: ArticleRemoteDataSource): ArticleDataSource.Remote

    @Binds
    @Singleton
    abstract fun bindArticleCacheDataSource(articleCacheDataSource: ArticleCacheDataSource): ArticleDataSource.Cache

}
