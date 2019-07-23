package com.nadarm.boardmvvmrx.domain.useCase

import com.nadarm.boardmvvmrx.domain.model.Article
import com.nadarm.boardmvvmrx.domain.repository.ArticleRepository
import io.reactivex.Flowable

class GetArticles(private val repository: ArticleRepository) : FlowableUseCase<Unit, List<Article>> {
    override fun execute(params: Unit): Flowable<List<Article>> {
        return repository.getAllArticles()
    }
}