package com.nadarm.boardmvvmrx.presentation.viewModel

import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.nadarm.boardmvvmrx.domain.model.Article
import com.nadarm.boardmvvmrx.domain.useCase.UpdateArticle
import io.reactivex.Observable
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface EditViewModel {

    interface Inputs {
        fun titleChanged(string: String)
        fun contentChanged(string: String)
        fun articleId(articleId: Long)
        fun saveClicked()
    }

    interface Outputs {
        fun finishActivity(): Observable<Unit>
        fun makeToast(): Observable<Pair<String, Int>>
    }

    class ViewModelImpl @Inject constructor(
        private val updateArticleUseCase: UpdateArticle
    ) : ViewModel(), Inputs, Outputs {

        private val titleChanged: PublishSubject<String> = PublishSubject.create()
        private val contentChanged: PublishSubject<String> = PublishSubject.create()
        private val articleId: PublishSubject<Long> = PublishSubject.create()
        private val saveClicked: PublishSubject<Unit> = PublishSubject.create()

        private val makeToast: BehaviorSubject<Pair<String, Int>> = BehaviorSubject.create()
        private val finishActivity: BehaviorSubject<Unit> = BehaviorSubject.create()

        val inputs: Inputs = this
        val outputs: Outputs = this

        init {

            val updateObservable = this.saveClicked
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .withLatestFrom(
                    articleId,
                    titleChanged,
                    contentChanged,
                    Function4<Unit, Long, String, String, Article> { _, articleId, title, content ->
                        return@Function4 Article(articleId, title, content)
                    }
                )
                .flatMapSingle(updateArticleUseCase::execute)
                .map { Unit }
                .subscribeOn(Schedulers.io())
                .share()

            updateObservable
                .map { "글이 수정됐습니다." to Toast.LENGTH_SHORT }
                .doOnError { this.makeToast.onNext("수정 실패" to Toast.LENGTH_SHORT) }
                .subscribe(this.makeToast)

            updateObservable
                .delay(600, TimeUnit.MILLISECONDS)
                .subscribe(this.finishActivity)
        }

        override fun finishActivity(): Observable<Unit> = this.finishActivity
        override fun makeToast(): Observable<Pair<String, Int>> = this.makeToast

        override fun titleChanged(string: String) {
            this.titleChanged.onNext(string)
        }

        override fun contentChanged(string: String) {
            this.contentChanged.onNext(string)
        }

        override fun articleId(articleId: Long) {
            this.articleId.onNext(articleId)
        }

        override fun saveClicked() {
            this.saveClicked.onNext(Unit)
        }

    }

}