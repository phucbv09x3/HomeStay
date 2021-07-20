package com.kujira.homestay.di.buidler

import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.kujira.homestay.ui.about.AboutFragment
import com.kujira.homestay.ui.account.AccountFragment
import com.kujira.homestay.ui.listRoom.ListRoomFragment
import com.kujira.homestay.ui.home.HomeFragment
import com.kujira.homestay.ui.list.ListFragment
import com.kujira.homestay.ui.login.LoginFragment
import com.kujira.homestay.ui.manager.ManagerRoomFragment
import com.kujira.homestay.ui.manager.ManagerRoomViewModel
import com.kujira.homestay.ui.register.RegisterFragment
import com.kujira.homestay.ui.travel.ListTravelFragment

@Module
abstract class MainFragmentProvider {
    @ContributesAndroidInjector
    internal abstract fun bindLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    internal abstract fun bindRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector
    internal abstract fun bindAboutFragment(): AboutFragment

    @ContributesAndroidInjector
    internal abstract fun bindListFragment(): ListFragment

    @ContributesAndroidInjector
    internal abstract fun bindHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    internal abstract fun bindListRoomFragment(): ListRoomFragment

    @ContributesAndroidInjector
    internal abstract fun bindAccountFragment(): AccountFragment

    @ContributesAndroidInjector
    internal abstract fun bindListTravelFragment(): ListTravelFragment
    @ContributesAndroidInjector
    internal abstract fun bindManagerRoomFragment(): ManagerRoomFragment
}