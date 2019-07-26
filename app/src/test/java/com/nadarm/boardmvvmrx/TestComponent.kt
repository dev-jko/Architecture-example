package com.nadarm.boardmvvmrx

import com.nadarm.boardmvvmrx.data.DataTestModule
import com.nadarm.boardmvvmrx.data.remote.ArticleRetrofitTest
import dagger.Component

@Component(modules = [DataTestModule::class])
interface TestComponent {

    fun inject(test: ArticleRetrofitTest)
}