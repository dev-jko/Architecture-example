package com.nadarm.boardmvvmrx.data.local

import androidx.room.*
import com.nadarm.boardmvvmrx.data.model.ArticleData
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
abstract class ArticleDao {
    @Query("SELECT * FROM articles ORDER BY articleId DESC")
    protected abstract fun getAllArticles(): Flowable<List<ArticleData>>

    fun getArticlesDistinct(): Flowable<List<ArticleData>> {
        return this.getAllArticles().distinctUntilChanged()
    }

    @Query("SELECT * FROM articles WHERE articleId = :articleId")
    protected abstract fun getArticle(articleId: Long): Flowable<ArticleData>

    fun getArticleDistinct(articleId: Long): Flowable<ArticleData> {
        return this.getArticle(articleId).distinctUntilChanged()
    }

    @Insert(onConflict = OnConflictStrategy.ABORT)
    abstract fun insertArticle(article: ArticleData): Single<Long>

    @Update
    abstract fun updateArticle(article: ArticleData): Single<Int>

    @Delete
    abstract fun deleteArticle(article: ArticleData): Single<Int>
}
