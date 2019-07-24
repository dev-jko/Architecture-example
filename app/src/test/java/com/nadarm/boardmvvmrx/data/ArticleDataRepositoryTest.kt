package com.nadarm.boardmvvmrx.data

import com.nadarm.boardmvvmrx.domain.model.Article
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subscribers.TestSubscriber
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class ArticleDataRepositoryTest {

    private lateinit var articleDataRepository: ArticleDataRepository
    private val articleLocalDataSource: ArticleDataSource.Local = mock(ArticleDataSource.Local::class.java)
    private val articleRemoteDataSource: ArticleDataSource.Remote = mock(ArticleDataSource.Remote::class.java)
    private val articles: List<Article> = listOf(
        Article(1, "title11", "content1"),
        Article(2, "title12", "content2"),
        Article(3, "title13", "content3")
    )
    private val compositeDisposable = CompositeDisposable()

    @Before
    fun setUp() {
        articleDataRepository = ArticleDataRepository(articleLocalDataSource, articleRemoteDataSource)
    }

    @After
    fun tearDown() {
        compositeDisposable.clear()
    }

    @Test
    fun `test getAllArticles local, remote success and same article list`() {
        `when`(articleLocalDataSource.getAllArticles()).thenReturn(Flowable.just(articles))
        `when`(articleRemoteDataSource.getAllArticles()).thenReturn(Flowable.just(articles))

        val test = articleDataRepository.getAllArticles().test()
        test.addTo(compositeDisposable)

        verify(articleLocalDataSource).getAllArticles()
        verify(articleRemoteDataSource).getAllArticles()

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(1)
        test.assertValues(articles)
    }

    @Test
    fun `test getAllArticles local success, remote fail`() {
        `when`(articleLocalDataSource.getAllArticles()).thenReturn(Flowable.just(articles))
        val throwable = Throwable()
        `when`(articleRemoteDataSource.getAllArticles()).thenReturn(Flowable.error(throwable))

        val test = articleDataRepository.getAllArticles().test()
        test.addTo(compositeDisposable)

        verify(articleLocalDataSource).getAllArticles()
        verify(articleRemoteDataSource).getAllArticles()

        test.assertValueCount(1)
        test.assertValue(articles)
        test.assertError(throwable)
        test.assertNotComplete()
    }

    @Test
    fun `test getAllArticles local fail, remote success`(){
        val throwable = Throwable()
        `when`(articleLocalDataSource.getAllArticles()).thenReturn(Flowable.error(throwable))
        `when`(articleRemoteDataSource.getAllArticles()).thenReturn(Flowable.just(articles))

        val test = articleDataRepository.getAllArticles().test()
        test.addTo(compositeDisposable)

        verify(articleLocalDataSource).getAllArticles()
        verify(articleRemoteDataSource).getAllArticles()

        test.assertNoErrors()
        test.assertValueCount(0)
        test.assertValues(emptyList(), articles)
        test.assertComplete()


    }

    @Test
    fun getArticle() {
    }

    @Test
    fun insertArticle() {
    }

    @Test
    fun updateArticle() {
    }

    @Test
    fun deleteArticle() {
    }
}