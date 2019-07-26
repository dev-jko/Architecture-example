package com.nadarm.boardmvvmrx.data

import com.nadarm.boardmvvmrx.AppSchedulers
import com.nadarm.boardmvvmrx.domain.model.Article
import com.nadarm.boardmvvmrx.domain.repository.ArticleRepository
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleDataRepository @Inject constructor(
    private val articleLocalDataSource: ArticleDataSource.Local,
    private val articleRemoteDataSource: ArticleDataSource.Remote,
    private val schedulers: AppSchedulers
) : ArticleRepository {

    override fun getAllArticles(): Flowable<List<Article>> {
        val local = articleLocalDataSource.getAllArticles().subscribeOn(schedulers.io())
        val remote = articleRemoteDataSource.getAllArticles().subscribeOn(schedulers.io())
        return Flowable.concat(
            remote.retry(2).doOnNext {
                articleLocalDataSource.deleteall
                it.forEach { article -> articleLocalDataSource.insertArticle(article).subscribeOn(schedulers.io()) }
            },

        )
            .distinctUntilChanged()
            .subscribeOn(schedulers.io())
    }

    override fun getArticle(articleId: Long): Flowable<Article> {
        val local = articleLocalDataSource.getArticle(articleId).subscribeOn(schedulers.io())
        val remote = articleRemoteDataSource.getArticle(articleId).subscribeOn(schedulers.io())
        return Flowable.concat(
            local.take(1).timeout(500, TimeUnit.MILLISECONDS).onErrorReturnItem(Article(0, "", "")),
            remote.retry(2)
        )
            .distinctUntilChanged()
            .subscribeOn(schedulers.io())
    }

    override fun insertArticle(article: Article): Single<Long> {
        return articleRemoteDataSource.insertArticle(article)
            .retry(2)
            .map { article.copy(articleId = it) }
            .flatMap(articleLocalDataSource::insertArticle)
            .subscribeOn(schedulers.io())
    }

    override fun updateArticle(article: Article): Single<Int> {
        // TODO add remote
        return articleLocalDataSource.updateArticle(article).subscribeOn(Schedulers.io())
    }

    override fun deleteArticle(article: Article): Single<Int> {
        // TODO add remote
        return articleLocalDataSource.deleteArticle(article).subscribeOn(Schedulers.io())
    }

}