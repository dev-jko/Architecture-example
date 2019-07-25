package com.nadarm.boardmvvmrx.data.remote.response

import com.google.gson.annotations.SerializedName

data class InsertArticleResponse(
    @SerializedName("name")
    val name: String
)