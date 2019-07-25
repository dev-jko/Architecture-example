package com.nadarm.boardmvvmrx.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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

    @Query("UPDATE articles SET title = :title, content = :content WHERE articleId = :articleId")
    protected abstract fun updateArticle(articleId: Long, title: String, content: String): Single<Int>

    fun updateArticle(articleData: ArticleData): Single<Int> {
        return this.updateArticle(articleData.articleId!!, articleData.title, articleData.content)
    }

    @Query("DELETE FROM articles WHERE articleId = :articleId")
    protected abstract fun deleteArticle(articleId: Long): Single<Int>

    fun deleteArticle(articleData: ArticleData): Single<Int> {
        return this.deleteArticle(articleData.articleId!!)
    }
}
