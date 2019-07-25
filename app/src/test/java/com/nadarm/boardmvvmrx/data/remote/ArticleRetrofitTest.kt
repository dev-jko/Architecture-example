package com.nadarm.boardmvvmrx.data.remote

import com.nadarm.boardmvvmrx.data.model.ArticleData
import org.junit.After
import org.junit.Before
import org.junit.Test

class ArticleRetrofitTest {

    private val articleRetrofit = ArticleRetrofit


    @Before
    fun setUp() {

    }

    @After
    fun tearDown() {
    }

    @Test
    fun getAllArticles() {
        val test = articleRetrofit.getAllArticles().test()

    }

    @Test
    fun insertArticle() {
        val articleData = ArticleData("test title", "test content")
        val result = articleRetrofit.insertArticle(articleData)

        val test = result.test()

        val throwable: Throwable = Throwable()
        test.assertComplete()
    }
}