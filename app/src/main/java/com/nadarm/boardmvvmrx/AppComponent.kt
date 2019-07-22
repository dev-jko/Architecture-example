package com.nadarm.boardmvvmrx

import com.nadarm.boardmvvmrx.data.DataSourceModule
import com.nadarm.boardmvvmrx.data.RepositoryModule
import com.nadarm.boardmvvmrx.presentation.viewModel.ListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataSourceModule::class, RepositoryModule::class], dependencies = [])
interface AppComponent {

    fun inject(viewModel: ListViewModel.ViewModel)

}