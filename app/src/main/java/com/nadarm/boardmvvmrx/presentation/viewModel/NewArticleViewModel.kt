package com.nadarm.boardmvvmrx.presentation.viewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.nadarm.boardmvvmrx.data.ArticleDataRepository
import com.nadarm.boardmvvmrx.BasicApp
import com.nadarm.boardmvvmrx.domain.model.Article
import io.reactivex.Observable
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

interface NewArticleViewModel {

    interface Inputs {
        fun titleChanged(title: String)
        fun contentChanged(content: String)
        fun saveClicked()
    }

    interface Outputs {
        fun finishActivity(): Observable<Unit>
        fun makeToast(): Observable<Pair<String, Int>>
        fun startDetailActivity(): Observable<Long>
    }

    class ViewModel(application: Application) : AndroidViewModel(application), Inputs, Outputs {
        private val repository: ArticleDataRepository by lazy { (application as BasicApp).getRepository() }

        private val titleChanged: PublishSubject<String> = PublishSubject.create()
        private val contentChanged: PublishSubject<String> = PublishSubject.create()
        private val saveClicked: PublishSubject<Unit> = PublishSubject.create()

        private val finishActivity: BehaviorSubject<Unit> = BehaviorSubject.create()
        private val makeToast: BehaviorSubject<Pair<String, Int>> = BehaviorSubject.create()
        private val startDetailActivity: Observable<Long>

        val inputs: Inputs = this
        val outputs: Outputs = this

        init {
            val insertionResult = this.saveClicked
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .withLatestFrom<String, String, Article>(
                    this.titleChanged,
                    this.contentChanged,
                    Function3 { _, title, content -> Article(title, content) }
                )
                .flatMapSingle(repository::insertArticle)
                .subscribeOn(Schedulers.io())
                .share()

            insertionResult
                .map { "글이 작성됐습니다." to Toast.LENGTH_SHORT }
                .doOnError { this.makeToast.onNext("작성 실패" to Toast.LENGTH_SHORT) }
                .subscribe(this.makeToast)

            insertionResult
                .map { Unit }
                .delay(800, TimeUnit.MILLISECONDS)
                .subscribe(this.finishActivity)

            this.startDetailActivity = insertionResult
                .delay(600, TimeUnit.MILLISECONDS)
        }

        override fun finishActivity(): Observable<Unit> = this.finishActivity
        override fun makeToast(): Observable<Pair<String, Int>> = this.makeToast
        override fun startDetailActivity(): Observable<Long> = this.startDetailActivity

        override fun titleChanged(title: String) {
            this.titleChanged.onNext(title)
        }

        override fun contentChanged(content: String) {
            this.contentChanged.onNext(content)
        }

        override fun saveClicked() {
            this.saveClicked.onNext(Unit)
        }

    }

}
