package com.nadarm.boardmvvmrx.domain.useCase

import com.nadarm.boardmvvmrx.domain.model.Article
import com.nadarm.boardmvvmrx.domain.repository.ArticleRepository
import io.reactivex.Single

class DeleteArticle(private val repository: ArticleRepository) : SingleUseCase<Article, Int> {
    override fun execute(params: Article): Single<Int> {
        return repository.deleteArticle(params)
    }
}