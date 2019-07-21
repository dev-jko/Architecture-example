package com.nadarm.boardmvvmrx.presentation.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.nadarm.boardmvvmrx.R
import com.nadarm.boardmvvmrx.databinding.ActivityEditBinding
import com.nadarm.boardmvvmrx.util.addTextChanged
import com.nadarm.boardmvvmrx.presentation.viewModel.DetailViewModel
import com.nadarm.boardmvvmrx.presentation.viewModel.EditViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo

class EditActivity : AppCompatActivity() {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val binding: ActivityEditBinding by lazy {
        DataBindingUtil.setContentView<ActivityEditBinding>(this, R.layout.activity_edit)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val detailVm = ViewModelProviders.of(this).get(DetailViewModel.ViewModel::class.java)
        val editVm = ViewModelProviders.of(this).get(EditViewModel.ViewModel::class.java)
        binding.detailVm = detailVm
        binding.editVm = editVm
        binding.lifecycleOwner = this


        detailVm.inputs.intent(intent)

        binding.editTitleEt.addTextChanged(editVm.inputs::titleChanged)
        binding.editContentEt.addTextChanged(editVm.inputs::contentChanged)


        detailVm.outputs.article()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { article ->
                binding.editTitleEt.setText(article.title)
                binding.editContentEt.setText(article.content)
            }
            .addTo(compositeDisposable)

        detailVm.outputs.article()
            .map { article -> article.articleId }
            .subscribe(editVm.inputs::articleId)
            .addTo(compositeDisposable)

        editVm.outputs.finishActivity()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { finish() }
            .addTo(compositeDisposable)

        editVm.outputs.makeToast()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { makeToast(it.first, it.second) }
            .addTo(compositeDisposable)

    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun makeToast(message: String, duration: Int) {
        Toast.makeText(application, message, duration).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.save_actionbar_actions, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.save_actionbar) {
            binding.editVm!!.inputs.saveClicked()
        }
        return super.onOptionsItemSelected(item)
    }
}
