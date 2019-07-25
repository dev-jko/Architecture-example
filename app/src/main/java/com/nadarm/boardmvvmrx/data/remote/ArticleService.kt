package com.nadarm.boardmvvmrx.data.remote

import com.nadarm.boardmvvmrx.data.model.ArticleData
import com.nadarm.boardmvvmrx.data.remote.response.InsertArticleResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.*

interface ArticleService {

    @GET("articles.json")
    fun getAllArticles(): Flowable<List<ArticleData>>

    @GET("articles/{articleId}.json")
    fun getArticle(@Path("articleId") articleId: Long): Flowable<ArticleData>

    @POST("articles.json")
    fun insertArticle(@Body articleData: ArticleData): Single<InsertArticleResponse>

    @PUT("articles.json")
    fun updateArticle(articleData: ArticleData): Single<Int>

    @DELETE("articles.json")
    fun deleteArticle(articleData: ArticleData): Single<Int>

}