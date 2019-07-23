package com.nadarm.boardmvvmrx.domain.useCase

import com.nadarm.boardmvvmrx.domain.model.Article
import com.nadarm.boardmvvmrx.domain.repository.ArticleRepository
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class DeleteArticleTest {

    private lateinit var deleteArticle: DeleteArticle
    private val mockArticleRepository: ArticleRepository = mock(ArticleRepository::class.java)
    private val article = Article(1, "title", "content")

    @Before
    fun setUp() {
        deleteArticle = DeleteArticle(mockArticleRepository)
    }

    @Test
    fun `test delete article success`() {
        // given
        `when`(mockArticleRepository.deleteArticle(article)).thenReturn(Single.just(1))

        // when
        val test = deleteArticle.execute(article).test()

        // then
        verify(mockArticleRepository).deleteArticle(article)

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(1)
        test.assertValue(1)
    }

    @Test
    fun `test delete article fail`() {
        // given
        val throwable = Throwable()
        `when`(mockArticleRepository.deleteArticle(article)).thenReturn(Single.error(throwable))

        // when
        val test = deleteArticle.execute(article).test()

        // then
        verify(mockArticleRepository).deleteArticle(article)

        test.assertNoValues()
        test.assertNotComplete()
        test.assertError(throwable)
        test.assertValueCount(0)
    }

}