package com.nadarm.boardmvvmrx.data.local

import com.nadarm.boardmvvmrx.data.ArticleDataSource
import com.nadarm.boardmvvmrx.data.model.mapper.ArticleDataMapper
import com.nadarm.boardmvvmrx.domain.model.Article
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleLocalDataSource @Inject constructor(
    private val articleDao: ArticleDao,
    private val mapper: ArticleDataMapper
) : ArticleDataSource.Local {

    override fun getAllArticles(): Flowable<List<Article>> {
        return articleDao.getArticlesDistinct()
            .map(mapper::mapFromData)
    }

    override fun getArticle(articleId: Long): Flowable<Article> {
        return articleDao.getArticleDistinct(articleId)
            .map(mapper::mapFromData)
    }

    override fun insertArticle(article: Article): Single<Long> {
        val data = mapper.mapToData(article)
        return articleDao.insertArticle(data)
    }

    override fun updateArticle(article: Article): Single<Int> {
        val data = mapper.mapToData(article)
        return articleDao.updateArticle(data)
    }

    override fun deleteArticle(article: Article): Single<Int> {
        val data = mapper.mapToData(article)
        return articleDao.deleteArticle(data)
    }

    override fun deleteAll(): Single<Int> {
        return articleDao.deleteAll()
    }
}