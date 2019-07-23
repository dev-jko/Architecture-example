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
import com.nadarm.boardmvvmrx.databinding.ActivityDetailBinding
import com.nadarm.boardmvvmrx.presentation.viewModel.DeleteViewModel
import com.nadarm.boardmvvmrx.presentation.viewModel.DetailViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class DetailActivity : AppCompatActivity() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val binding: ActivityDetailBinding by lazy {
        DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val detailVm = ViewModelProviders.of(this).get(DetailViewModel.ViewModel::class.java)
        val deleteVm = ViewModelProviders.of(this).get(DeleteViewModel.ViewModel::class.java)


        binding.detailVm = detailVm
        binding.deleteVm = deleteVm
        binding.lifecycleOwner = this

        detailVm.inputs.intent(intent)

        detailVm.outputs.article()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { article ->
                binding.detailTitleTv.text = article.title
                binding.detailContentTv.text = article.content
            }
            .addTo(compositeDisposable)

        detailVm.outputs.article()
            .subscribe { article -> deleteVm.inputs.article(article) }
            .addTo(compositeDisposable)

        detailVm.outputs.startEditActivity()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { startEditActivity(it) }
            .addTo(compositeDisposable)

        deleteVm.outputs.finishActivity()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { this.finish() }
            .addTo(compositeDisposable)

        deleteVm.outputs.makeToast()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { this.makeToast(it.first, it.second) }
            .addTo(compositeDisposable)
    }

    private fun makeToast(message: String, duration: Int) {
        Toast.makeText(application, message, duration).show()
    }

    private fun startEditActivity(articleId: Long) {
        val intent = Intent(this, EditActivity::class.java)
        println(articleId)
        intent.putExtra("articleId", articleId)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.edit_actionbar_actions, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.edit_actionbar -> binding.detailVm!!.inputs.editClicked()
            R.id.delete_actionbar -> binding.deleteVm!!.inputs.deleteClicked()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}