package com.nadarm.boardmvvmrx.data.remote

import com.nadarm.boardmvvmrx.data.model.ArticleData
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class ArticleRetrofit @Inject constructor(
    private val service: ArticleService
) {

    fun getAllArticles(): Flowable<List<ArticleData>> {
        return this.service.getAllArticles()
            .map { it.articleDataList }
    }

    fun getArticle(articleId: Long): Flowable<ArticleData> {
        return this.service.getArticle(articleId)
            .map { it.articleData }
    }

    fun insertArticle(articleData: ArticleData): Single<Long> {
        articleData.articleId = null
        return this.service.insertArticle(articleData)
            .map { it.articleId }
    }

    fun updateArticle(articleData: ArticleData): Single<Int> {
        if (articleData.articleId == null) {
            // TODO throwable def
            return Single.error(Throwable())
        }
        return this.service.updateArticle(articleData.articleId!!, articleData)
    }

    fun deleteArticle(articleData: ArticleData): Single<Int> {
        if (articleData.articleId == null) {
            // TODO throwable def
            return Single.error(Throwable())
        }
        return this.service.deleteArticle(articleData.articleId!!)
    }
}