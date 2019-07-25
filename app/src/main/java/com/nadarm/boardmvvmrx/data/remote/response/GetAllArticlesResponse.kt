package com.nadarm.boardmvvmrx.data.remote.response

import com.nadarm.boardmvvmrx.data.model.ArticleData

data class GetAllArticlesResponse(

    val name:String,
    val articleData:ArticleData
)