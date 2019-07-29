package com.nadarm.boardmvvmrx.data

import com.nadarm.boardmvvmrx.AppSchedulers
import com.nadarm.boardmvvmrx.domain.model.Article
import com.nadarm.boardmvvmrx.domain.repository.ArticleRepository
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleDataRepository @Inject constructor(
//    private val articleLocalDataSource: ArticleDataSource.Local,
    private val articleCacheDataSource: ArticleDataSource.Cache,
    private val articleRemoteDataSource: ArticleDataSource.Remote,
    private val schedulers: AppSchedulers
) : ArticleRepository {

    override fun getAllArticles(): Flowable<List<Article>> {
        return if (articleCacheDataSource.isFresh()) {
            articleCacheDataSource.getAllArticles().subscribeOn(schedulers.io())
        } else {
            articleRemoteDataSource.getAllArticles()
                .retry(2)
                .doOnNext(articleCacheDataSource::insertAll)
                .subscribeOn(schedulers.io())
        }
    }

    override fun getArticle(articleId: Long): Flowable<Article> {
        return if (articleCacheDataSource.isFresh(articleId)) {
            articleCacheDataSource.getArticle(articleId).subscribeOn(schedulers.io())
        } else {
            articleRemoteDataSource.getArticle(articleId)
                .retry(2)
                .doOnNext { articleCacheDataSource.updateArticle(it) }
                .subscribeOn(schedulers.io())
        }.subscribeOn(schedulers.io())
    }

    override fun insertArticle(article: Article): Single<Long> {
        return articleRemoteDataSource.insertArticle(article)
            .retry(2)
            .map { article.copy(articleId = it) }
            .doAfterSuccess { articleCacheDataSource.insertArticle(it) }
            .map { it.articleId }
            .subscribeOn(schedulers.io())
    }

    override fun updateArticle(article: Article): Single<Int> {
        return articleRemoteDataSource.updateArticle(article)
            .retry(2)
            .doAfterSuccess { articleCacheDataSource.updateArticle(article) }
            .subscribeOn(Schedulers.io())
    }

    override fun deleteArticle(article: Article): Single<Int> {
        return articleRemoteDataSource.deleteArticle(article)
            .retry(2)
            .doOnSuccess { articleCacheDataSource.deleteArticle(article) }
            .subscribeOn(Schedulers.io())
    }

}