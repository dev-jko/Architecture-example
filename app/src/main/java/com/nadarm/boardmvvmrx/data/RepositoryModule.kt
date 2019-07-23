package com.nadarm.boardmvvmrx.data

import com.nadarm.boardmvvmrx.domain.repository.ArticleRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun providesArticleRepository(articleDataRepository: ArticleDataRepository): ArticleRepository

}