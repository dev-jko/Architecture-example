package com.nadarm.boardmvvmrx.data.local

import com.nadarm.boardmvvmrx.data.model.mapper.ArticleLocalMapper
import com.nadarm.boardmvvmrx.domain.model.Article
import com.nadarm.boardmvvmrx.domain.repository.ArticleRepository
import io.reactivex.Flowable
import io.reactivex.Single

class ArticleLocalDataSource private constructor(
    private val articleDao: ArticleDao,
    private val mapper: ArticleLocalMapper
) : ArticleRepository {

    override fun getAllArticles(): Flowable<List<Article>> {
        return articleDao.getAllArticles()
            .map(mapper::mapFromData)
    }

    override fun getArticle(articleId: Long): Flowable<Article> {
        return articleDao.getArticle(articleId)
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

    companion object {
        private var INSTANCE: ArticleLocalDataSource? = null

        fun getInstance(articleDao: ArticleDao, dataMapper: ArticleLocalMapper): ArticleLocalDataSource {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = ArticleLocalDataSource(articleDao, dataMapper)
                }
            }
            return INSTANCE!!
        }
    }
}