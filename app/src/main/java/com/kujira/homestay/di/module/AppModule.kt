package com.kujira.homestay.di.module

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.kujira.homestay.data.AppDataManager
import com.kujira.homestay.data.DataManager
import com.kujira.homestay.data.api.ApiService
import com.kujira.homestay.data.api.IApiService
import com.kujira.homestay.data.local.DataStoreHelper
import com.kujira.homestay.data.local.DataStoreManager
import com.kujira.homestay.data.scheduler.AppSchedulerProvider
import com.kujira.homestay.data.scheduler.ISchedulerProvider
import com.kujira.homestay.ui.base.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun providerDataStore(dataStoreManager: DataStoreManager): DataStoreHelper {
        return dataStoreManager
    }

    @Provides
    @Singleton
    internal fun provideDataManager(appDataManager: AppDataManager): DataManager {
        return appDataManager
    }

    @Provides
    @Singleton
    internal fun provideScheduler(): ISchedulerProvider {
        return AppSchedulerProvider()
    }

    @Provides
    @Singleton
    fun viewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory {
        return factory
    }


    @Provides
    @Singleton
    fun providerApiService(): IApiService {
        return ApiService()
    }

}