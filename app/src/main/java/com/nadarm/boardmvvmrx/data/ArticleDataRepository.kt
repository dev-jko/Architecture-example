package com.nadarm.boardmvvmrx.data

import com.nadarm.boardmvvmrx.AppSchedulers
import com.nadarm.boardmvvmrx.domain.model.Article
import com.nadarm.boardmvvmrx.domain.repository.ArticleRepository
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
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
            local.takeUntil(remote).onErrorReturnItem(emptyList()),
            remote.onErrorResumeNext(local)
        ).distinctUntilChanged()
    }

    override fun getArticle(articleId: Long): Flowable<Article> {
        // TODO add remote
        return articleLocalDataSource.getArticle(articleId).subscribeOn(Schedulers.io())
    }

    override fun insertArticle(article: Article): Single<Long> {
        // TODO add remote
        return articleLocalDataSource.insertArticle(article).subscribeOn(Schedulers.io())
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