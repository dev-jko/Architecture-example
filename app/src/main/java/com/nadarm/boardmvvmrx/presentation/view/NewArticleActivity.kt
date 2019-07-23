package com.nadarm.boardmvvmrx.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.nadarm.boardmvvmrx.R
import com.nadarm.boardmvvmrx.databinding.ActivityNewArticleBinding
import com.nadarm.boardmvvmrx.util.addTextChanged
import com.nadarm.boardmvvmrx.presentation.viewModel.NewArticleViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class NewArticleActivity : AppCompatActivity() {

    private val TAG = NewArticleActivity::class.java.simpleName
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val binding: ActivityNewArticleBinding by lazy {
        DataBindingUtil.setContentView<ActivityNewArticleBinding>(this, R.layout.activity_new_article)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vm = ViewModelProviders.of(this).get(NewArticleViewModel.ViewModel::class.java)
        binding.vm = vm
        binding.lifecycleOwner = this

        binding.newTitleEt.addTextChanged(vm.inputs::titleChanged)
        binding.newContentEt.addTextChanged(vm.inputs::contentChanged)


        vm.outputs.finishActivity()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { this@NewArticleActivity.finish() }
            .addTo(compositeDisposable)

        vm.outputs.makeToast()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { this@NewArticleActivity.makeToast(it.first, it.second) }
            .addTo(compositeDisposable)

        vm.outputs.startDetailActivity()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { this@NewArticleActivity.startDetailActivity(it) }
            .addTo(compositeDisposable)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.save_actionbar_actions, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.save_actionbar) {
            binding.vm!!.inputs.saveClicked()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun makeToast(message: String, duration: Int) {
        Toast.makeText(application, message, duration).show()
    }

    private fun startDetailActivity(articleId: Long) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("articleId", articleId)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}
