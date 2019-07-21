package com.nadarm.boardmvvmrx.domain.repository


import com.nadarm.boardmvvmrx.domain.model.Article
import io.reactivex.Flowable
import io.reactivex.Single

interface ArticleRepository {

    fun getAllArticles(): Flowable<List<Article>>

    fun getArticle(articleId: Long): Flowable<Article>

    fun insertArticle(article: Article): Single<Long>

    fun updateArticle(article: Article): Single<Int>

    fun deleteArticle(article: Article): Single<Int>
}