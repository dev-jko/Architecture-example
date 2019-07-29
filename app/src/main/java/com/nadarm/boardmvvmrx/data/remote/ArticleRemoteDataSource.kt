package com.nadarm.boardmvvmrx.data.remote

import com.nadarm.boardmvvmrx.data.ArticleDataSource
import com.nadarm.boardmvvmrx.data.model.mapper.ArticleDataMapper
import com.nadarm.boardmvvmrx.domain.model.Article
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleRemoteDataSource @Inject constructor(
    private val articleRetrofit: ArticleRetrofit,
    private val mapper: ArticleDataMapper
) : ArticleDataSource.Remote {

    override fun getAllArticles(): Flowable<List<Article>> {
        return articleRetrofit.getAllArticles()
            .distinctUntilChanged()
            .map(mapper::mapFromData)
    }

    override fun getArticle(articleId: Long): Flowable<Article> {
        return articleRetrofit.getArticle(articleId)
            .map(mapper::mapFromData)
    }

    override fun insertArticle(article: Article): Single<Long> {
        val articleData = mapper.mapToData(article)
        return articleRetrofit.insertArticle(articleData)
    }

    override fun updateArticle(article: Article): Single<Int> {
        val articleData = mapper.mapToData(article)
        return articleRetrofit.updateArticle(articleData)
    }

    override fun deleteArticle(article: Article): Single<Int> {
        val articleData = mapper.mapToData(article)
        return articleRetrofit.deleteArticle(articleData)
    }
}