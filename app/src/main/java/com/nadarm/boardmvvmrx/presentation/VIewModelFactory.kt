package com.nadarm.boardmvvmrx.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nadarm.boardmvvmrx.presentation.viewModel.*
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
class ViewModelFactory @Inject constructor(
    private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModels[modelClass]?.get() as T
    }

}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.PROPERTY_GETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel.ViewModelImpl::class)
    internal abstract fun listViewModel(viewModel: ListViewModel.ViewModelImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DeleteViewModel.ViewModelImpl::class)
    internal abstract fun deleteViewModel(viewModel: DeleteViewModel.ViewModelImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailViewModel.ViewModelImpl::class)
    internal abstract fun detailViewModel(viewModel: DetailViewModel.ViewModelImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EditViewModel.ViewModelImpl::class)
    internal abstract fun editViewModel(viewModel: EditViewModel.ViewModelImpl): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewArticleViewModel.ViewModelImpl::class)
    internal abstract fun newArticleViewModel(viewModel: NewArticleViewModel.ViewModelImpl): ViewModel
}