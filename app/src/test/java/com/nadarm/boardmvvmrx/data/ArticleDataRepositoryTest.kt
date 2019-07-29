package com.nadarm.boardmvvmrx.data

import com.nadarm.boardmvvmrx.AppSchedulers
import com.nadarm.boardmvvmrx.domain.model.Article
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import java.util.concurrent.TimeUnit

class ArticleDataRepositoryTest {

    private lateinit var articleDataRepository: ArticleDataRepository
    //    private val articleLocalDataSource: ArticleDataSource.Local = mock(ArticleDataSource.Local::class.java)
    private val articleCacheDataSource: ArticleDataSource.Cache = mock(ArticleDataSource.Cache::class.java)
    private val articleRemoteDataSource: ArticleDataSource.Remote = mock(ArticleDataSource.Remote::class.java)
    private val schedulers: AppSchedulers = mock(AppSchedulers::class.java)
    private val articles: List<Article> = listOf(
        Article(1, "title11", "content1"),
        Article(2, "title12", "content2"),
        Article(3, "title13", "content3")
    )
    private val compositeDisposable = CompositeDisposable()
    private lateinit var testScheduler: TestScheduler

    @Before
    fun setUp() {
        testScheduler = TestScheduler()
        `when`(schedulers.ui()).thenReturn(this.testScheduler)
        `when`(schedulers.io()).thenReturn(this.testScheduler)
        `when`(schedulers.computation()).thenReturn(this.testScheduler)
        articleDataRepository = ArticleDataRepository(articleCacheDataSource, articleRemoteDataSource, schedulers)
    }

    @After
    fun tearDown() {
        compositeDisposable.clear()
    }

    @Test
    fun `test getAllArticles local, remote success and same article list`() {
        // TODO implement test
    }

    @Test
    fun getArticle() {
        // TODO implement test
    }

    @Test
    fun insertArticle() {
        // TODO implement test
    }

    @Test
    fun updateArticle() {
        // TODO implement test
    }

    @Test
    fun deleteArticle() {
        // TODO implement test
    }
}