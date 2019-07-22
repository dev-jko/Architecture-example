package com.nadarm.boardmvvmrx.data

import com.nadarm.boardmvvmrx.data.local.ArticleLocalDataSource
import com.nadarm.boardmvvmrx.data.remote.ArticleRemoteDataSource
import com.nadarm.boardmvvmrx.domain.repository.ArticleRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindsArticleRepository(articleDataRepository: ArticleDataRepository): ArticleRepository

    @Binds
    abstract fun bindsArticleLocalDataSource(articleLocalDataSource: ArticleLocalDataSource): ArticleDataSource.Local

    @Binds
    abstract fun bindsArticleRemoteDataSource(articleRemoteDataSource: ArticleRemoteDataSource): ArticleDataSource.Remote

}