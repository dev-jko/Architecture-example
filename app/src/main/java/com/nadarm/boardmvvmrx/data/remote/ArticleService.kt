package com.nadarm.boardmvvmrx.data.remote

import com.nadarm.boardmvvmrx.data.model.ArticleData
import com.nadarm.boardmvvmrx.data.remote.response.GetAllArticlesResponse
import com.nadarm.boardmvvmrx.data.remote.response.GetArticleResponse
import com.nadarm.boardmvvmrx.data.remote.response.InsertArticleResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.*

interface ArticleService {

    @GET("articles/")
    fun getAllArticles(): Flowable<GetAllArticlesResponse>

    @GET("articles/{articleId}")
    fun getArticle(@Path("articleId") articleId: Long): Flowable<GetArticleResponse>

    @POST("articles/")
    fun insertArticle(@Body articleData: ArticleData): Single<InsertArticleResponse>

    @PUT("articles/{articleId}")
    fun updateArticle(@Path("articleId") articleId: Long, @Body articleData: ArticleData): Single<Int>

    @DELETE("articles/{articleId}")
    fun deleteArticle(@Path("articleId") articleId: Long): Single<Int>

}