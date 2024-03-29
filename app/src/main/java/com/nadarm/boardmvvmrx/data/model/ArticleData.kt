package com.nadarm.boardmvvmrx.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "articles")
data class ArticleData(

    @ColumnInfo(name = "title")
    @SerializedName("title")
    val title: String,

    @ColumnInfo(name = "content")
    @SerializedName("content")
    val content: String
) {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "articleId")
    @SerializedName("articleId")
    var articleId: Long? = null

    @Ignore
    var updatedAt: Long? = null

    constructor(articleId: Long, title: String, content: String) : this(title, content) {
        this.articleId = articleId
    }
}