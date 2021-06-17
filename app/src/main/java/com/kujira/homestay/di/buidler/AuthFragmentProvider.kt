package com.kujira.homestay.di.buidler

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.kujira.homestay.ui.login.LoginFragment

@Module
abstract class AuthFragmentProvider {

    @ContributesAndroidInjector()
    internal abstract fun bindLoginFragment(): LoginFragment
}