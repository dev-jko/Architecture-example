package com.nadarm.boardmvvmrx.data

import com.nadarm.boardmvvmrx.domain.repository.ArticleRepository

interface ArticleDataSource : ArticleRepository {

    interface Local : ArticleDataSource

    interface Remote : ArticleDataSource

}