package com.nadarm.boardmvvmrx.data.remote

import com.nadarm.boardmvvmrx.data.ArticleDataSource
import com.nadarm.boardmvvmrx.data.model.mapper.ArticleDataMapper
import com.nadarm.boardmvvmrx.domain.model.Article
import io.reactivex.Flowable
import io.reactivex.Single

object ArticleRemoteDataSource : ArticleDataSource.Remote {

    private val articleRetrofit: ArticleRetrofit = ArticleRetrofit
    private val mapper: ArticleDataMapper = ArticleDataMapper

    override fun getAllArticles(): Flowable<List<Article>> {
        return articleRetrofit.getAllArticles().distinctUntilChanged().map(mapper::mapFromData)
    }

    override fun getArticle(articleId: Long): Flowable<Article> {
        // TODO
        return Flowable.empty()
    }

    override fun insertArticle(article: Article): Single<Long> {
        val articleData = mapper.mapToData(article)
//        return articleRetrofit.insertArticle(articleData)
        return Single.just(1)
    }

    override fun updateArticle(article: Article): Single<Int> {
        // TODO
        return Single.create { 1 }
    }

    override fun deleteArticle(article: Article): Single<Int> {
        // TODO
        return Single.create { 1 }
    }
}