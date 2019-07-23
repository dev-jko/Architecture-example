package com.nadarm.boardmvvmrx

import com.nadarm.boardmvvmrx.data.DataSourceModule
import com.nadarm.boardmvvmrx.data.RepositoryModule
import com.nadarm.boardmvvmrx.presentation.ViewModelModule
import com.nadarm.boardmvvmrx.presentation.view.DetailActivity
import com.nadarm.boardmvvmrx.presentation.view.EditActivity
import com.nadarm.boardmvvmrx.presentation.view.ListActivity
import com.nadarm.boardmvvmrx.presentation.view.NewArticleActivity
import com.nadarm.boardmvvmrx.presentation.viewModel.ListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataSourceModule::class, RepositoryModule::class, ViewModelModule::class], dependencies = [])
interface AppComponent {

    fun inject(activity: ListActivity)
    fun inject(activity: DetailActivity)
    fun inject(activity: NewArticleActivity)
    fun inject(activity: EditActivity)

}