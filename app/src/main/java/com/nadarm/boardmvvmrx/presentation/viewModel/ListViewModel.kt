package com.nadarm.boardmvvmrx.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.nadarm.boardmvvmrx.domain.model.Article
import com.nadarm.boardmvvmrx.domain.useCase.GetArticles
import com.nadarm.boardmvvmrx.presentation.view.adapter.ArticleAdapter
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface ListViewModel {
    interface Inputs : ArticleAdapter.Delegate {
        fun newArticleClicked()
    }

    interface Outputs {
        fun articles(): Flowable<List<Article>>
        fun startDetailActivity(): Observable<Article>
        fun startNewArticleActivity(): Observable<Boolean>
    }

    class ViewModelImpl @Inject constructor(
        private val getArticlesUseCase: GetArticles
    ) : ViewModel(), Inputs, Outputs {
        private val articleClicked: PublishSubject<Article> = PublishSubject.create()
        private val newArticleClicked: PublishSubject<Boolean> = PublishSubject.create()

        private val articles: Flowable<List<Article>> = this.getArticlesUseCase.execute(Unit)
        private val startDetailActivity: Observable<Article> =
            articleClicked.throttleFirst(500, TimeUnit.MILLISECONDS)
        private val startNewArticleActivity: Observable<Boolean> =
            newArticleClicked.throttleFirst(500, TimeUnit.MILLISECONDS)

        val inputs: Inputs = this
        val outputs: Outputs = this

        override fun articles(): Flowable<List<Article>> = this.articles
        override fun startDetailActivity(): Observable<Article> = this.startDetailActivity
        override fun startNewArticleActivity(): Observable<Boolean> = this.startNewArticleActivity

        override fun newArticleClicked() {
            this.newArticleClicked.onNext(true)
        }

        override fun articleClicked(article: Article) {
            this.articleClicked.onNext(article)
        }

    }
}