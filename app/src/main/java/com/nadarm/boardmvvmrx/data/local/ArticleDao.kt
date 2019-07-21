package com.nadarm.boardmvvmrx.data.local

import androidx.room.*
import com.nadarm.boardmvvmrx.data.model.ArticleData
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface ArticleDao {
    @Query("SELECT * FROM articles ORDER BY articleId DESC")
    fun getAllArticles(): Flowable<List<ArticleData>>

    @Query("SELECT * FROM articles WHERE articleId = :articleId")
    fun getArticle(articleId: Long): Flowable<ArticleData>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertArticle(article: ArticleData): Single<Long>

    @Update
    fun updateArticle(article: ArticleData): Single<Int>

    @Delete
    fun deleteArticle(article: ArticleData): Single<Int>
}
