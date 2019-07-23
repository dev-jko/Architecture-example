package com.nadarm.boardmvvmrx.domain.useCase

import com.nadarm.boardmvvmrx.domain.model.Article
import com.nadarm.boardmvvmrx.domain.repository.ArticleRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class InsertArticleTest {

    private lateinit var insertArticle: InsertArticle
    private val mockArticleRepository: ArticleRepository = mock(ArticleRepository::class.java)
    private val article = Article(1, "title", "content")

    @Before
    fun setUp() {
        insertArticle = InsertArticle(mockArticleRepository)
    }

    @Test
    fun `test insert article success`() {
        `when`(mockArticleRepository.insertArticle(article)).thenReturn(Single.just(1))

        val test = insertArticle.execute(article).test()

        verify(mockArticleRepository).insertArticle(article)

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(1)
        test.assertValue(1)
    }

    @Test
    fun `test insert article fail`() {
        val throwable = Throwable()
        `when`(mockArticleRepository.insertArticle(article)).thenReturn(Single.error(throwable))

        val test = insertArticle.execute(article).test()

        verify(mockArticleRepository).insertArticle(article)

        test.assertNoValues()
        test.assertNotComplete()
        test.assertValueCount(0)
        test.assertError(throwable)
    }
}