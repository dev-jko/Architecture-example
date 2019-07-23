package com.nadarm.boardmvvmrx.domain.useCase

import com.nadarm.boardmvvmrx.domain.model.Article
import com.nadarm.boardmvvmrx.domain.repository.ArticleRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class UpdateArticleTest {

    private lateinit var updateArticle: UpdateArticle
    private val mockArticleRepository: ArticleRepository = mock(ArticleRepository::class.java)
    private val article = Article(1, "title", "content")

    @Before
    fun setUp() {
        updateArticle = UpdateArticle(mockArticleRepository)
    }

    @Test
    fun `update article success`() {
        `when`(mockArticleRepository.updateArticle(article)).thenReturn(Single.just(1))

        val test = updateArticle.execute(article).test()

        verify(mockArticleRepository).updateArticle(article)

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(1)
        test.assertValue(1)
    }

    @Test
    fun `update article fail`() {
        val throwable = Throwable()
        `when`(mockArticleRepository.updateArticle(article)).thenReturn(Single.error(throwable))

        val test = updateArticle.execute(article).test()

        verify(mockArticleRepository).updateArticle(article)

        test.assertNoValues()
        test.assertValueCount(0)
        test.assertNotComplete()
        test.assertError(throwable)
    }
}