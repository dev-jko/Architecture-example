package com.nadarm.boardmvvmrx.presentation.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.nadarm.boardmvvmrx.AppModule
import com.nadarm.boardmvvmrx.DaggerAppComponent
import com.nadarm.boardmvvmrx.R
import com.nadarm.boardmvvmrx.data.DataSourceModule
import com.nadarm.boardmvvmrx.databinding.ActivityMainBinding
import com.nadarm.boardmvvmrx.domain.model.Article
import com.nadarm.boardmvvmrx.presentation.view.adapter.ArticleAdapter
import com.nadarm.boardmvvmrx.presentation.viewModel.ListViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListActivity : AppCompatActivity() {

    private val TAG = ListActivity::class.java.simpleName
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val binding: ActivityMainBinding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val vm = ViewModelProviders.of(this).get(ListViewModel.ViewModelImpl::class.java)

        DaggerAppComponent.builder()
            .appModule(AppModule(application))
            .dataSourceModule(DataSourceModule())
            .build()
            .inject(this)


        val vm = ViewModelProviders.of(this, viewModelFactory).get(ListViewModel.ViewModelImpl::class.java)

        binding.vm = vm
        binding.lifecycleOwner = this

        val adapter: ArticleAdapter = ArticleAdapter(vm)
        binding.rvArticle.adapter = adapter

        vm.outputs.articles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { articles -> adapter.refresh(articles) }
            .addTo(compositeDisposable)
        vm.outputs.startDetailActivity()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { article -> startDetailActivity(article) }
            .addTo(compositeDisposable)
        vm.outputs.startNewArticleActivity()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { startNewArticleActivity() }
            .addTo(compositeDisposable)

        binding.floatingActionButton.setOnClickListener { vm.inputs.newArticleClicked() }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun startDetailActivity(article: Article) {
        val intent = Intent(this@ListActivity, DetailActivity::class.java)
        intent.putExtra("articleId", article.articleId)
        startActivity(intent)
    }

    private fun startNewArticleActivity() {
        val intent = Intent(this, NewArticleActivity::class.java)
        startActivity(intent)
    }

}
