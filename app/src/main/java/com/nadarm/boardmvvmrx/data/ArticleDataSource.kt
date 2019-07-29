package com.nadarm.boardmvvmrx.data

import com.nadarm.boardmvvmrx.domain.model.Article
import com.nadarm.boardmvvmrx.domain.repository.ArticleRepository
import io.reactivex.Single

interface ArticleDataSource : ArticleRepository {

    interface Local : ArticleDataSource {
        fun deleteAll(): Single<Int>
    }

    interface Cache : ArticleDataSource {
        fun isFresh(): Boolean
        fun isFresh(articleId: Long): Boolean
        fun insertAll(articles: List<Article>)
    }

    interface Remote : ArticleDataSource

}