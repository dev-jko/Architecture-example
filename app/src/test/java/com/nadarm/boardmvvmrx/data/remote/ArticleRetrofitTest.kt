package com.nadarm.boardmvvmrx.data.remote

import com.nadarm.boardmvvmrx.DaggerTestComponent
import com.nadarm.boardmvvmrx.data.DataTestModule
import com.nadarm.boardmvvmrx.data.model.ArticleData
import com.nadarm.boardmvvmrx.data.remote.response.GetAllArticlesResponse
import com.nadarm.boardmvvmrx.data.remote.response.InsertArticleResponse
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import javax.inject.Inject

class ArticleRetrofitTest {

    private lateinit var articleRetrofit: ArticleRetrofit
    private val mockService: ArticleService = mock(ArticleService::class.java)
    private val compositeDisposable = CompositeDisposable()

    @Inject
    lateinit var articleDataList: List<ArticleData>
    @Inject
    lateinit var articleData: ArticleData

    @Before
    fun setUp() {
        DaggerTestComponent.builder()
            .dataTestModule(DataTestModule())
            .build()
            .inject(this)

        articleRetrofit = ArticleRetrofit(mockService)
    }

    @After
    fun tearDown() {
        compositeDisposable.clear()
    }

    @Test
    fun `test getAllArticles success`() {
        val response = GetAllArticlesResponse(articleDataList)
        `when`(mockService.getAllArticles()).thenReturn(Flowable.just(response))

        articleRetrofit.getAllArticles()
            .test()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue(articleDataList)
            .assertComplete()
            .addTo(compositeDisposable)

        verify(mockService).getAllArticles()
    }

    @Test
    fun `test getAllArticles fail`() {
        val throwable = Throwable()
        `when`(mockService.getAllArticles()).thenReturn(Flowable.error(throwable))

        articleRetrofit.getAllArticles()
            .test()
            .assertValueCount(0)
            .assertNotComplete()
            .assertError(throwable)
            .addTo(compositeDisposable)

        verify(mockService).getAllArticles()
    }

    @Test
    fun `test insertArticle success`() {
        val anotherArticleData = this.articleData.copy()
        val response = InsertArticleResponse(this.articleData.articleId!!)
        `when`(mockService.insertArticle(anotherArticleData)).thenReturn(Single.just(response))

        articleRetrofit.insertArticle(anotherArticleData)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValueCount(1)
            .assertValue(this.articleData.articleId)
            .addTo(compositeDisposable)

        verify(mockService).insertArticle(anotherArticleData)
    }

    @Test
    fun `test insertArticle fail`() {
        val anotherArticleData = articleData.copy()
        val throwable = Throwable()
        `when`(mockService.insertArticle(anotherArticleData)).thenReturn(Single.error(throwable))

        articleRetrofit.insertArticle(anotherArticleData)
            .test()
            .assertValueCount(0)
            .assertNotComplete()
            .assertError(throwable)
            .addTo(compositeDisposable)

        verify(mockService).insertArticle(anotherArticleData)
    }
}