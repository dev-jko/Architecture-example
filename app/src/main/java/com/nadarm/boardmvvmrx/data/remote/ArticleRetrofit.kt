package com.nadarm.boardmvvmrx.data.remote

import com.nadarm.boardmvvmrx.data.model.ArticleData
import com.nadarm.boardmvvmrx.data.remote.response.InsertArticleResponse
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ArticleRetrofit {
    private const val baseUrl = "https://android-crud-70d88.firebaseio.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(this.baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    private val service: ArticleService = retrofit.create(ArticleService::class.java)

    fun getAllArticles(): Flowable<List<ArticleData>> {
        return this.service.getAllArticles()
    }

    fun insertArticle(articleData: ArticleData): Single<InsertArticleResponse> {
        return this.service.insertArticle(articleData)
    }
}