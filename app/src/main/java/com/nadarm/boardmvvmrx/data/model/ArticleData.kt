package com.nadarm.boardmvvmrx.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "articles")
data class ArticleData(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String
) {
    @PrimaryKey(autoGenerate = true)
    var articleId: Long = 0

    constructor(articleId: Long, title: String, content: String) : this(title, content) {
        this.articleId = articleId
    }
}