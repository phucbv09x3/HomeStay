package com.kujira.homestay.di.buidler

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.kujira.homestay.ui.main.MainActivity
import com.kujira.homestay.ui.map.MapActivity

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [MainFragmentProvider::class])
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [MainFragmentProvider::class])
    internal abstract fun bindMapActivity(): MapActivity

}