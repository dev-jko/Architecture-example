package com.nadarm.boardmvvmrx.data.remote

import com.nadarm.boardmvvmrx.domain.model.Article
import com.nadarm.boardmvvmrx.domain.repository.ArticleRepository
import io.reactivex.Flowable
import io.reactivex.Single

object ArticleRemoteDataSource : ArticleRepository {
    override fun getAllArticles(): Flowable<List<Article>> {
        // TODO
        return Flowable.empty()
    }

    override fun getArticle(articleId: Long): Flowable<Article> {
        // TODO
        return Flowable.empty()
    }

    override fun insertArticle(article: Article): Single<Long> {
        // TODO
        return Single.create { 1 }
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