package com.kujira.homestay.di.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import com.kujira.homestay.App
import com.kujira.homestay.di.buidler.ActivityBuilder
import com.kujira.homestay.di.module.AppModule
import javax.inject.Singleton

/**
 * Created by Phucbv on 5/2021
 */
@Singleton
@Component(modules = [AndroidSupportInjectionModule::class, ActivityBuilder::class, AppModule::class])
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }


}