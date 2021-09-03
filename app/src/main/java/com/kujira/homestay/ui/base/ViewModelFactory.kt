package com.kujira.homestay.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.kujira.homestay.data.DataManager
import com.kujira.homestay.data.api.IApiService
import com.kujira.homestay.data.scheduler.ISchedulerProvider
import com.kujira.homestay.ui.about.AboutViewModel
import com.kujira.homestay.ui.all_login.login_new.LoginAccViewModel
import com.kujira.homestay.ui.all_login.register_new.RegisterAccViewModel
import com.kujira.homestay.ui.client.account.AccountViewModel
import com.kujira.homestay.ui.client.comment.CommentViewModel
import com.kujira.homestay.ui.client.home.HomeViewModel
import com.kujira.homestay.ui.client.listRoom.ListRoomViewModel
import com.kujira.homestay.ui.client.main.MainViewModel
import com.kujira.homestay.ui.client.manager.ManagerRoomViewModel
import com.kujira.homestay.ui.client.map.MapViewModel
import com.kujira.homestay.ui.client.travelAll.TravelAllViewModel
import com.kujira.homestay.ui.client.weather.WeatherViewModel
import com.kujira.homestay.ui.host.add.AddRoomViewModel
import com.kujira.homestay.ui.host.main.MainHostViewModel
import com.kujira.homestay.ui.host.manager.ManagerRoomHostViewModel
import com.kujira.homestay.ui.host.myacc.MyAccountHostViewModel
import com.kujira.homestay.ui.splash.SplashViewModel
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
            modelClass.isAssignableFrom(AboutViewModel::class.java) -> AboutViewModel() as T
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> HomeViewModel() as T
            modelClass.isAssignableFrom(ListRoomViewModel::class.java) -> ListRoomViewModel() as T
            modelClass.isAssignableFrom(AccountViewModel::class.java) -> AccountViewModel() as T

            modelClass.isAssignableFrom(ManagerRoomViewModel::class.java) -> ManagerRoomViewModel() as T
            modelClass.isAssignableFrom(TravelAllViewModel::class.java) -> TravelAllViewModel() as T
            modelClass.isAssignableFrom(MapViewModel::class.java) -> MapViewModel() as T
            modelClass.isAssignableFrom(WeatherViewModel::class.java) -> WeatherViewModel() as T
            modelClass.isAssignableFrom(LoginAccViewModel::class.java) -> LoginAccViewModel() as T
            modelClass.isAssignableFrom(RegisterAccViewModel::class.java) -> RegisterAccViewModel() as T

            modelClass.isAssignableFrom(MainHostViewModel::class.java) -> MainHostViewModel() as T
            modelClass.isAssignableFrom(ManagerRoomHostViewModel::class.java) -> ManagerRoomHostViewModel() as T
            modelClass.isAssignableFrom(AddRoomViewModel::class.java) -> AddRoomViewModel() as T
            modelClass.isAssignableFrom(MyAccountHostViewModel::class.java) -> MyAccountHostViewModel() as T

            modelClass.isAssignableFrom(SplashViewModel::class.java) -> SplashViewModel() as T
            modelClass.isAssignableFrom(CommentViewModel::class.java) -> CommentViewModel() as T


            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
        if (viewModel is BaseViewModel) {
            viewModel.initData(dataManager, apiService, scheduler)
        }
        return viewModel

    }
}