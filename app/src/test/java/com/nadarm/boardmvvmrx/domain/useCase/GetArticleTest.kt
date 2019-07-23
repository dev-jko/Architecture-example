package com.nadarm.boardmvvmrx.domain.useCase

import com.nadarm.boardmvvmrx.domain.model.Article
import com.nadarm.boardmvvmrx.domain.repository.ArticleRepository
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class GetArticleTest {

    private lateinit var getArticle: GetArticle
    private val mockArticleRepository: ArticleRepository = mock(ArticleRepository::class.java)
    private val article = Article(1, "title", "content")

    @Before
    fun setUp() {
        getArticle = GetArticle(mockArticleRepository)
    }

    @Test
    fun `test get article success`() {

        // given
        `when`(mockArticleRepository.getArticle(1)).thenReturn(Flowable.just(article))

        // when
        val test = getArticle.execute(1).test()

        // then
        verify(mockArticleRepository).getArticle(1)

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(1)
        test.assertValue(article)
    }

    @Test
    fun `test get article fail`() {

        val throwable = Throwable()
        `when`(mockArticleRepository.getArticle(1)).thenReturn(Flowable.error(throwable))

        val test = getArticle.execute(1).test()

        verify(mockArticleRepository).getArticle(1)

        test.assertNoValues()
        test.assertValueCount(0)
        test.assertNotComplete()
        test.assertError(throwable)
    }

}