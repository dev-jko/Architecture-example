package com.nadarm.boardmvvmrx.domain.useCase

import com.nadarm.boardmvvmrx.domain.model.Article
import com.nadarm.boardmvvmrx.domain.repository.ArticleRepository
import io.reactivex.Flowable
import javax.inject.Inject

class GetArticle @Inject constructor(private val repository: ArticleRepository) : FlowableUseCase<Long, Article> {

    override fun execute(params: Long): Flowable<Article> {
        return repository.getArticle(params)
    }

}