package com.nadarm.boardmvvmrx

import android.app.Application
import com.nadarm.boardmvvmrx.data.DataSourceModule

class AndroidApplication : Application() {

    private val appComponent = DaggerAppComponent.builder()
        .appModule(AppModule(this))
        .dataSourceModule(DataSourceModule())
        .build()

    fun getAppComponent(): AppComponent = this.appComponent


}