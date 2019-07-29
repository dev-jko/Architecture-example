package com.nadarm.boardmvvmrx.data.remote.response

import com.google.gson.annotations.SerializedName
import com.nadarm.boardmvvmrx.data.model.ArticleData

data class GetAllArticlesResponse(
    @SerializedName("articles")
    val articleDataList: List<ArticleData>
)