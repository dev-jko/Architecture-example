package com.nadarm.boardmvvmrx.data.remote

import com.nadarm.boardmvvmrx.data.model.ArticleData
import com.nadarm.boardmvvmrx.data.remote.response.GetAllArticlesResponse
import com.nadarm.boardmvvmrx.data.remote.response.GetArticleResponse
import com.nadarm.boardmvvmrx.data.remote.response.InsertArticleResponse
import io.reactivex.Flowable
import io.reactivex.Single

class FakeArticleService : ArticleService {

    private val articleDataList: MutableList<ArticleData> = listOf(
        ArticleData(1, "title1", "content1"),
        ArticleData(2, "title2", "content2"),
        ArticleData(3, "title3", "content3"),
        ArticleData(4, "title4", "content4"),
        ArticleData(5, "title5", "content5")
    ).toMutableList()
    private var lastIndex: Long = 5

    override fun getAllArticles(): Flowable<GetAllArticlesResponse> {
        return Flowable.just(GetAllArticlesResponse(articleDataList))
    }

    override fun getArticle(articleId: Long): Flowable<GetArticleResponse> {
        val result = articleDataList.first { it.articleId == articleId }
        return Flowable.just(GetArticleResponse(result))
    }

    override fun insertArticle(articleData: ArticleData): Single<InsertArticleResponse> {
        articleData.articleId = ++this.lastIndex
        articleDataList.add(articleData)
        return Single.just(InsertArticleResponse(lastIndex))
    }

    override fun updateArticle(articleId: Long, articleData: ArticleData): Single<Int> {
        val targetIndex = articleDataList.indexOfFirst { it.articleId == articleId }
        articleDataList[targetIndex] = articleData
        return Single.just(1)
    }

    override fun deleteArticle(articleId: Long): Single<Int> {
        val targetIndex = articleDataList.indexOfFirst { it.articleId == articleId }
        articleDataList.removeAt(targetIndex)
        return Single.just(1)
    }
}