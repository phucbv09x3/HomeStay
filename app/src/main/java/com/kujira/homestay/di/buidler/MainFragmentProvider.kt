package com.kujira.homestay.di.buidler

import com.kujira.homestay.ui.about.AboutFragment
import com.kujira.homestay.ui.client.account.AccountFragment
import com.kujira.homestay.ui.client.home.HomeFragment
import com.kujira.homestay.ui.client.listRoom.ListRoomFragment
import com.kujira.homestay.ui.client.manager.ManagerRoomFragment
import com.kujira.homestay.ui.client.weather.WeatherFragment
import com.kujira.homestay.ui.host.add.AddRoomFragment
import com.kujira.homestay.ui.host.manager.ManagerRoomHostFragment
import com.kujira.homestay.ui.host.myacc.MyAccountHostFragment
import com.kujira.homestay.ui.client.travelAll.TravelAllFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentProvider {
    @ContributesAndroidInjector
    internal abstract fun bindAboutFragment(): AboutFragment


    @ContributesAndroidInjector
    internal abstract fun bindHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    internal abstract fun bindListRoomFragment(): ListRoomFragment

    @ContributesAndroidInjector
    internal abstract fun bindAccountFragment(): AccountFragment


    @ContributesAndroidInjector
    internal abstract fun bindManagerRoomFragment(): ManagerRoomFragment

    @ContributesAndroidInjector
    internal abstract fun bindTravelAllFragment(): TravelAllFragment

    @ContributesAndroidInjector
    internal abstract fun bindWeatherFragment(): WeatherFragment

    @ContributesAndroidInjector
    internal abstract fun bindMyAccHostFragment(): MyAccountHostFragment

    @ContributesAndroidInjector
    internal abstract fun bindAddRoomFragment(): AddRoomFragment

    @ContributesAndroidInjector
    internal abstract fun bindManagerRoomHostFragment(): ManagerRoomHostFragment

}