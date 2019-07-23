package com.nadarm.boardmvvmrx.domain.useCase

import com.nadarm.boardmvvmrx.domain.model.Article
import com.nadarm.boardmvvmrx.domain.repository.ArticleRepository
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class GetArticlesTest {

    private lateinit var getArticles: GetArticles
    private val mockArticleRepository: ArticleRepository = mock(ArticleRepository::class.java)
    private val articles: List<Article> = listOf(Article(1, "title", "content"))

    @Before
    fun setUp() {
        getArticles = GetArticles(mockArticleRepository)
    }

    @Test
    fun `test get articles success`() {
        `when`(mockArticleRepository.getAllArticles()).thenReturn(Flowable.just(articles))

        val test = getArticles.execute(Unit).test()

        verify(mockArticleRepository).getAllArticles()

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(1)
        test.assertValue(articles)
    }

    @Test
    fun `test get articles fail`() {
        val throwable = Throwable()
        `when`(mockArticleRepository.getAllArticles()).thenReturn(Flowable.error(throwable))

        val test = getArticles.execute(Unit).test()

        verify(mockArticleRepository).getAllArticles()

        test.assertValueCount(0)
        test.assertError(throwable)
        test.assertNotComplete()
        test.assertNoValues()
    }
}