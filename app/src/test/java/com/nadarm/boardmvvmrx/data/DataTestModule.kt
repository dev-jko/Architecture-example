package com.nadarm.boardmvvmrx.data

import com.nadarm.boardmvvmrx.data.model.ArticleData
import dagger.Module
import dagger.Provides
import kotlin.random.Random


@Module
class DataTestModule {

    private val random = Random(500)

    private fun createArticleData(): ArticleData {
        val num = random.nextLong()
        val title = "test title $num"
        val content = "test content $num"
        return ArticleData(num, title, content)
    }

    @Provides
    fun provideArticleData(): ArticleData {
        return this.createArticleData()
    }

    @Provides
    fun provideArticleDataList(): List<ArticleData> {
        return List(10) { this.createArticleData() }
    }

}