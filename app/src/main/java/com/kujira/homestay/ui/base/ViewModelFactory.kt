package com.kujira.homestay.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kujira.homestay.data.DataManager
import com.kujira.homestay.data.api.IApiService
import com.kujira.homestay.data.scheduler.ISchedulerProvider
import com.kujira.homestay.ui.about.AboutViewModel
import com.kujira.homestay.ui.account.AccountViewModel
import com.kujira.homestay.ui.all_login.login_new.LoginAccViewModel
import com.kujira.homestay.ui.all_login.register_new.RegisterAccViewModel
import com.kujira.homestay.ui.home.HomeViewModel
import com.kujira.homestay.ui.list.ListViewModel
import com.kujira.homestay.ui.listRoom.ListRoomViewModel
import com.kujira.homestay.ui.login.LoginViewModel
import com.kujira.homestay.ui.main.MainViewModel
import com.kujira.homestay.ui.manager.ManagerRoomViewModel
import com.kujira.homestay.ui.map.MapViewModel
import com.kujira.homestay.ui.register.RegisterViewModel
import com.kujira.homestay.ui.travel.ListTravelViewModel
import com.kujira.homestay.ui.travelAll.TravelAllViewModel
import com.kujira.homestay.ui.weather.WeatherViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewModelFactory @Inject constructor(
    private val dataManager: DataManager,
    private val apiService: IApiService,
    private val scheduler: ISchedulerProvider
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        val viewModel = when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> MainViewModel() as T
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> LoginViewModel() as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> RegisterViewModel() as T
            modelClass.isAssignableFrom(AboutViewModel::class.java) -> AboutViewModel() as T
            modelClass.isAssignableFrom(ListViewModel::class.java) -> ListViewModel() as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel() as T
            modelClass.isAssignableFrom(ListRoomViewModel::class.java) -> ListRoomViewModel() as T
            modelClass.isAssignableFrom(AccountViewModel::class.java) -> AccountViewModel() as T
            modelClass.isAssignableFrom(ListTravelViewModel::class.java) -> ListTravelViewModel() as T
            modelClass.isAssignableFrom(ManagerRoomViewModel::class.java) -> ManagerRoomViewModel() as T
            modelClass.isAssignableFrom(TravelAllViewModel::class.java) -> TravelAllViewModel() as T
            modelClass.isAssignableFrom(MapViewModel::class.java) -> MapViewModel() as T
            modelClass.isAssignableFrom(WeatherViewModel::class.java) -> WeatherViewModel() as T
            modelClass.isAssignableFrom(LoginAccViewModel::class.java) -> LoginAccViewModel() as T
            modelClass.isAssignableFrom(RegisterAccViewModel::class.java) -> RegisterAccViewModel() as T

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
        if (viewModel is BaseViewModel) {
            viewModel.initData(dataManager, apiService, scheduler)
        }
        return viewModel

    }
}