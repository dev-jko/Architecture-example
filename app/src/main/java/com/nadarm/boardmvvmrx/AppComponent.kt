package com.nadarm.boardmvvmrx

import android.app.Application
import com.nadarm.boardmvvmrx.data.ArticleDataRepository
import com.nadarm.boardmvvmrx.data.LocalDataModule
import com.nadarm.boardmvvmrx.data.local.ArticleDao
import com.nadarm.boardmvvmrx.data.local.ArticleDatabase
import com.nadarm.boardmvvmrx.data.local.ArticleLocalDataSource
import com.nadarm.boardmvvmrx.data.remote.ArticleRemoteDataSource
import com.nadarm.boardmvvmrx.data.RepositoryModule
import com.nadarm.boardmvvmrx.domain.repository.ArticleRepository
import com.nadarm.boardmvvmrx.domain.useCase.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, LocalDataModule::class, RepositoryModule::class], dependencies = [])
interface AppComponent {

    fun articleDao(): ArticleDao
    fun articleDatabase(): ArticleDatabase
    fun articleLocalDataSource(): ArticleLocalDataSource
    fun articleRemoteDataSource(): ArticleRemoteDataSource
    fun articleDataRepository(): ArticleDataRepository
    fun articleRepository(): ArticleRepository

    fun getArticle(): GetArticle
    fun getArticles(): GetArticles
    fun insertArticle(): InsertArticle
    fun updateArticle(): UpdateArticle
    fun deleteArticle(): DeleteArticle

    fun application(): Application
}