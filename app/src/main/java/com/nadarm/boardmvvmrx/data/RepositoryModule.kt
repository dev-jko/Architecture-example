package com.nadarm.boardmvvmrx.data

import com.nadarm.boardmvvmrx.data.local.ArticleLocalDataSource
import com.nadarm.boardmvvmrx.data.remote.ArticleRemoteDataSource
import com.nadarm.boardmvvmrx.domain.repository.ArticleRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindArticleRepository(articleDataRepository: ArticleDataRepository): ArticleRepository

    @Binds
    abstract fun bindArticleLocalDataSource(articleLocalDataSource: ArticleLocalDataSource): ArticleDataSource.Local

    @Binds
    abstract fun bindArticleRemoteDataSource(articleRemoteDataSource: ArticleRemoteDataSource): ArticleDataSource.Remote

}