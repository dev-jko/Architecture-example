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
    private val articleLocalDataSource: ArticleDataSource.Local = mock(ArticleDataSource.Local::class.java)
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
        articleDataRepository = ArticleDataRepository(articleLocalDataSource, articleRemoteDataSource, schedulers)
    }

    @After
    fun tearDown() {
        compositeDisposable.clear()
    }

    @Test
    fun `test getAllArticles local, remote success and same article list`() {
        `when`(articleLocalDataSource.getAllArticles()).thenReturn(
            Flowable.just(articles)
                .delay(100, TimeUnit.MILLISECONDS, testScheduler)
        )
        `when`(articleRemoteDataSource.getAllArticles()).thenReturn(
            Flowable.just(articles)
                .delay(500, TimeUnit.MILLISECONDS, testScheduler)
        )

        val test =
            articleDataRepository.getAllArticles()
                .subscribeOn(testScheduler)
                .test()
        test.addTo(compositeDisposable)

        verify(articleLocalDataSource).getAllArticles()
        verify(articleRemoteDataSource).getAllArticles()


        test.assertValueCount(0)

        testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS)
        test.assertValueCount(1)
        test.assertValues(articles)

        testScheduler.advanceTimeBy(500, TimeUnit.MILLISECONDS)
        test.assertValueCount(1)
        test.assertValue(articles)

        test.assertComplete()
        test.assertNoErrors()
    }

    @Test
    fun `test getAllArticles local, remote success and different article list`() {
        `when`(articleLocalDataSource.getAllArticles()).thenReturn(
            Flowable.just(articles)
                .delay(100, TimeUnit.MILLISECONDS, testScheduler)
        )
        val anotherArticles = articles.map { article ->
            Article(article.articleId + 5, article.title + "changed", article.content + "changed")
        }
        `when`(articleRemoteDataSource.getAllArticles()).thenReturn(
            Flowable.just(anotherArticles)
                .delay(500, TimeUnit.MILLISECONDS, testScheduler)
        )

        val test =
            articleDataRepository.getAllArticles()
                .subscribeOn(testScheduler)
                .test()
        test.addTo(compositeDisposable)

        verify(articleLocalDataSource).getAllArticles()
        verify(articleRemoteDataSource).getAllArticles()


        test.assertValueCount(0)

        testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS)
        test.assertValueCount(1)
        test.assertValue(articles)

        testScheduler.advanceTimeBy(500, TimeUnit.MILLISECONDS)
        test.assertValueCount(2)
        test.assertValues(articles, anotherArticles)

        test.assertComplete()
        test.assertNoErrors()
    }

    @Test
    fun `test getAllArticles local success, remote fail`() {
        `when`(articleLocalDataSource.getAllArticles()).thenReturn(
            Flowable.just(articles)
                .delay(100, TimeUnit.MILLISECONDS, testScheduler)
        )
        val throwable = Throwable()
        `when`(articleRemoteDataSource.getAllArticles()).thenReturn(
            Flowable.timer(500, TimeUnit.MILLISECONDS)
                .flatMap { Flowable.error<List<Article>>(throwable) }
        )

        val test =
            articleDataRepository.getAllArticles()
                .subscribeOn(testScheduler)
                .test()
        test.addTo(compositeDisposable)

        verify(articleLocalDataSource).getAllArticles()
        verify(articleRemoteDataSource).getAllArticles()

        test.assertValueCount(0)

        testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS)
        test.assertValueCount(1)
        test.assertValue(articles)

        testScheduler.advanceTimeBy(500, TimeUnit.MILLISECONDS)
        test.assertValueCount(1)
        test.assertValue(articles)

        test.assertNoErrors()
        test.assertNotComplete()
    }

    @Test
    fun `test getAllArticles local fail, remote success`() {
        val throwable = Throwable()
        `when`(articleLocalDataSource.getAllArticles()).thenReturn(
            Flowable.timer(100, TimeUnit.MILLISECONDS, testScheduler)
                .flatMap { Flowable.error<List<Article>>(throwable) }
        )
        `when`(articleRemoteDataSource.getAllArticles()).thenReturn(
            Flowable.just(articles)
                .delay(500, TimeUnit.MILLISECONDS, testScheduler)
        )

        val test =
            articleDataRepository.getAllArticles()
                .subscribeOn(testScheduler)
                .test()
        test.addTo(compositeDisposable)

        verify(articleLocalDataSource).getAllArticles()
        verify(articleRemoteDataSource).getAllArticles()

        test.assertValueCount(0)

        testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS)
        test.assertValueCount(1)
        test.assertValue(emptyList())

        testScheduler.advanceTimeBy(500, TimeUnit.MILLISECONDS)
        test.assertValueCount(2)
        test.assertValues(emptyList(), articles)

        test.assertComplete()
    }

    @Test
    fun `test getAllArticles local, remote fail`() {
        val throwable = Throwable()
        `when`(articleLocalDataSource.getAllArticles()).thenReturn(
            Flowable.timer(100, TimeUnit.MILLISECONDS, testScheduler)
                .flatMap { Flowable.error<List<Article>>(throwable) }
        )
        `when`(articleRemoteDataSource.getAllArticles()).thenReturn(
            Flowable.timer(500, TimeUnit.MILLISECONDS, testScheduler)
                .flatMap { Flowable.error<List<Article>>(throwable) }
        )

        val test =
            articleDataRepository.getAllArticles()
                .subscribeOn(testScheduler)
                .test()
        test.addTo(compositeDisposable)

        verify(articleLocalDataSource).getAllArticles()
        verify(articleRemoteDataSource).getAllArticles()

        test.assertValueCount(0)

        testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS)
        test.assertValueCount(1)
        test.assertValue(emptyList())

        testScheduler.advanceTimeBy(500, TimeUnit.MILLISECONDS)
        test.assertValueCount(1)
        test.assertValue(emptyList())

        test.assertNoErrors()
        test.assertNotComplete()
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