package com.nadarm.boardmvvmrx.domain.useCase

import com.nadarm.boardmvvmrx.domain.model.Article
import com.nadarm.boardmvvmrx.domain.repository.ArticleRepository
import io.reactivex.Single
import javax.inject.Inject

class UpdateArticle @Inject constructor(private val repository: ArticleRepository) : SingleUseCase<Article, Int> {
    override fun execute(params: Article): Single<Int> {
        return repository.updateArticle(params)
    }
}