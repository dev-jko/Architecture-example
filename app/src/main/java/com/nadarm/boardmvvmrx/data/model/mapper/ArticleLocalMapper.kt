package com.nadarm.boardmvvmrx.data.model.mapper

import com.nadarm.boardmvvmrx.data.model.ArticleData
import com.nadarm.boardmvvmrx.domain.model.Article

object ArticleLocalMapper {

    fun mapFromData(articleData: ArticleData): Article = Article(
        articleData.articleId,
        articleData.title,
        articleData.content
    )

    fun mapFromData(articles: List<ArticleData>): List<Article> = articles.map { this.mapFromData(it) }


    fun mapToData(article: Article): ArticleData = ArticleData(
        article.articleId,
        article.title,
        article.content
    )

    fun mapToData(articles: List<Article>): List<ArticleData> = articles.map { this.mapToData(it) }

}