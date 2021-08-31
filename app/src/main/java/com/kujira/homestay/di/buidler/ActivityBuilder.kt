package com.kujira.homestay.di.buidler

import com.kujira.homestay.ui.all_login.login_new.LoginActivity
import com.kujira.homestay.ui.all_login.register_new.RegisterActivity
import com.kujira.homestay.ui.host.main.MainHostActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.kujira.homestay.ui.client.main.MainActivity
import com.kujira.homestay.ui.client.map.MapActivity

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = [MainFragmentProvider::class])
    internal abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [MainFragmentProvider::class])
    internal abstract fun bindMapActivity(): MapActivity

    @ContributesAndroidInjector(modules = [MainFragmentProvider::class])
    internal abstract fun bindLoginActivity(): LoginActivity

    @ContributesAndroidInjector(modules = [MainFragmentProvider::class])
    internal abstract fun bindRegisterActivity(): RegisterActivity

    @ContributesAndroidInjector(modules = [MainFragmentProvider::class])
    internal abstract fun bindMainHostActivity(): MainHostActivity
}