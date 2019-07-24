package com.nadarm.boardmvvmrx.data.model.mapper

import com.nadarm.boardmvvmrx.data.model.ArticleData
import com.nadarm.boardmvvmrx.domain.model.Article
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ArticleLocalMapperTest {

    private lateinit var mapper: ArticleLocalMapper
    private val title1 = "title1"
    private val title2 = "title2"
    private val content1 = "content1"
    private val content2 = "content2"

    @Before
    fun setUp() {
        mapper = ArticleLocalMapper
    }

    @Test
    fun `test map from data`() {
        val articleData = ArticleData(1, title1, content1)
        val article = mapper.mapFromData(articleData)

        assertArticle(articleData, article)
    }

    @Test
    fun `test map from data list`() {
        val articleDataList = listOf(
            ArticleData(1, title1, content1),
            ArticleData(2, title2, content2)
        )
        val articleList = mapper.mapFromData(articleDataList)

        assertEquals(articleDataList.size, articleList.size)
        articleDataList.zip(articleList) { articleData, article -> assertArticle(articleData, article) }
    }

    @Test
    fun `test map to data`() {
        val article = Article(1, title1, content1)
        val articleData = mapper.mapToData(article)

        assertArticle(articleData, article)
    }

    @Test
    fun `test map to data list`() {
        val articleList = listOf(
            Article(1, title1, content1),
            Article(2, title2, content2)
        )
        val articleDataList = mapper.mapToData(articleList)

        assertEquals(articleList.size, articleDataList.size)
        articleList.zip(articleDataList) { article, articleData -> assertArticle(articleData, article) }
    }

    private fun assertArticle(articleData: ArticleData, article: Article) {
        assertEquals(articleData.articleId, article.articleId)
        assertEquals(articleData.title, article.title)
        assertEquals(articleData.content, article.content)
    }
}