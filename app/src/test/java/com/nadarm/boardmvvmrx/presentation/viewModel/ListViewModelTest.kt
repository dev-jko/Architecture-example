package com.nadarm.boardmvvmrx.presentation.viewModel

import com.nadarm.boardmvvmrx.AppSchedulers
import com.nadarm.boardmvvmrx.domain.model.Article
import com.nadarm.boardmvvmrx.domain.useCase.GetArticles
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.TestObserver
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import java.util.concurrent.TimeUnit

class ListViewModelTest {

    private lateinit var listViewModel: ListViewModel.ViewModelImpl
    private lateinit var testScheduler: TestScheduler
    private val useCase: GetArticles = mock(GetArticles::class.java)
    private val articles = listOf(
        Article(1, "title1", "content1"),
        Article(2, "title2", "content2")
    )
    private val compositeDisposable = CompositeDisposable()
    private val schedulers: AppSchedulers = mock(AppSchedulers::class.java)

    @Before
    fun setUp() {
        testScheduler = TestScheduler()
        `when`(schedulers.ui()).thenReturn(testScheduler)
        `when`(schedulers.io()).thenReturn(testScheduler)
        `when`(schedulers.computation()).thenReturn(testScheduler)
    }

    @After
    fun tearDown() {
        compositeDisposable.clear()
    }

    @Test
    fun `test get articles success`() {
        `when`(useCase.execute(Unit)).thenReturn(Flowable.just(articles))
        listViewModel = ListViewModel.ViewModelImpl(useCase, schedulers)

        val test = listViewModel.outputs.articles().test()

        verify(useCase).execute(Unit)

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(1)
        test.assertValue(articles)

        test.addTo(compositeDisposable)
    }

    @Test
    fun `test get articles success whit no articles`() {
        `when`(useCase.execute(Unit)).thenReturn(Flowable.just(emptyList()))
        listViewModel = ListViewModel.ViewModelImpl(useCase, schedulers)

        val test = listViewModel.outputs.articles().test()

        verify(useCase).execute(Unit)

        test.assertNoErrors()
        test.assertComplete()
        test.assertValueCount(1)
        test.assertValues(emptyList())

        test.addTo(compositeDisposable)
    }

    @Test
    fun `test get articles fail`() {
        val throwable = Throwable()
        `when`(useCase.execute(Unit)).thenReturn(Flowable.error(throwable))
        listViewModel = ListViewModel.ViewModelImpl(useCase, schedulers)

        val test = listViewModel.outputs.articles().test()

        verify(useCase).execute(Unit)

        test.assertValueCount(0)
        test.assertNoValues()
        test.assertNotComplete()
        test.assertError(throwable)

        test.addTo(compositeDisposable)
    }

    @Test
    fun `test article clicked`() {
        listViewModel = ListViewModel.ViewModelImpl(useCase, schedulers)
        val article = articles[0]
        val testObserver = TestObserver<Article>()


        Observable.interval(250, TimeUnit.MILLISECONDS, testScheduler)
            .subscribe { listViewModel.inputs.articleClicked(article) }
            .addTo(compositeDisposable)

        listViewModel.outputs.startDetailActivity()
            .subscribe(testObserver)
        testObserver.addTo(compositeDisposable)


        testObserver.assertSubscribed()
        testObserver.assertValueCount(0)

        testScheduler.advanceTimeBy(500, TimeUnit.MILLISECONDS)
        testObserver.assertValueCount(1)

        testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)
        testObserver.assertValueCount(3)

        testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)
        testObserver.assertValueCount(5)

        testObserver.assertValues(article, article, article, article, article)
        testObserver.assertNoErrors()

    }

    @Test
    fun `test new article clicked`() {
        listViewModel = ListViewModel.ViewModelImpl(useCase, schedulers)
        val testObserver = TestObserver<Unit>()

        Observable.interval(250, TimeUnit.MILLISECONDS, testScheduler)
            .subscribe { listViewModel.inputs.newArticleClicked() }
            .addTo(compositeDisposable)

        listViewModel.outputs.startNewArticleActivity()
            .subscribe(testObserver)
        testObserver.addTo(compositeDisposable)


        testObserver.assertSubscribed()
        testObserver.assertValueCount(0)

        testScheduler.advanceTimeBy(500, TimeUnit.MILLISECONDS)
        testObserver.assertValueCount(1)

        testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)
        testObserver.assertValueCount(3)

        testScheduler.advanceTimeBy(1000, TimeUnit.MILLISECONDS)
        testObserver.assertValueCount(5)

        testObserver.assertValues(Unit, Unit, Unit, Unit, Unit)
        testObserver.assertNoErrors()

    }

}