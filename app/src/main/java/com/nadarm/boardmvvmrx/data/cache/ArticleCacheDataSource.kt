package com.nadarm.boardmvvmrx.data.cache

import com.nadarm.boardmvvmrx.data.ArticleDataSource
import com.nadarm.boardmvvmrx.data.model.ArticleData
import com.nadarm.boardmvvmrx.data.model.mapper.ArticleDataMapper
import com.nadarm.boardmvvmrx.domain.model.Article
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleCacheDataSource @Inject constructor(
    private val mapper: ArticleDataMapper
) : ArticleDataSource.Cache {

    private val timeout = 5 * 60 * 1000
    private val articleDataList: ArrayList<ArticleData> = ArrayList()
    private var updateAt: Long = 0

    override fun isFresh(): Boolean {
        val current = System.currentTimeMillis()
        return current - this.updateAt < this.timeout
    }

    override fun isFresh(articleId: Long): Boolean {
        val updateAt = this.articleDataList
            .first { it.articleId == articleId }
            .updatedAt
        val current = System.currentTimeMillis()
        return current - updateAt!! < this.timeout
    }

    override fun insertAll(articles: List<Article>) {
        this.updateAt = System.currentTimeMillis()
        this.articleDataList.clear()
        articles.map(mapper::mapToData)
            .forEach {
                it.updatedAt = this.updateAt
                this.articleDataList.add(it)
            }
    }

    override fun getAllArticles(): Flowable<List<Article>> {
        return Flowable.just(articleDataList).map(mapper::mapFromData)
    }

    override fun getArticle(articleId: Long): Flowable<Article> {
        val result = articleDataList.first { it.articleId == articleId }
        return Flowable.just(result).map(mapper::mapFromData)
    }

    override fun insertArticle(article: Article): Single<Long> {
        val articleData = ArticleData(article.articleId, article.title, article.content)
        articleData.updatedAt = System.currentTimeMillis()
        articleDataList.add(articleData)
        return Single.just(articleData.articleId)
    }

    override fun updateArticle(article: Article): Single<Int> {
        val targetIndex = articleDataList.indexOfFirst { it.articleId == article.articleId }
        val articleData = ArticleData(article.articleId, article.title, article.content)
        articleData.updatedAt = System.currentTimeMillis()
        articleDataList[targetIndex] = articleData
        return Single.just(1)
    }

    override fun deleteArticle(article: Article): Single<Int> {
        val targetIndex = articleDataList.indexOfFirst { it.articleId == article.articleId }
        articleDataList.removeAt(targetIndex)
        return Single.just(1)
    }


}