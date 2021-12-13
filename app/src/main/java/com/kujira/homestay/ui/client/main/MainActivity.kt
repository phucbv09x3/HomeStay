package com.kujira.homestay.ui.client.main

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.kujira.homestay.R
import com.kujira.homestay.databinding.ActivityMainBinding
import com.kujira.homestay.ui.base.BaseActivity
import com.kujira.homestay.ui.base.BaseFragment
import com.kujira.homestay.ui.client.BlockActivity
import com.kujira.homestay.ui.client.account.AccountFragment
import com.kujira.homestay.ui.client.home.HomeFragment
import com.kujira.homestay.ui.client.manager.ManagerRoomFragment
import com.kujira.homestay.ui.client.service.HomeStayService
import com.kujira.homestay.utils.printLog


open class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(),
    NavController.OnDestinationChangedListener {
    private lateinit var navController: NavController
    private lateinit var currentFragment: BaseFragment<*, *>
    private var currentFragmentId: Int = R.id.home_fragment
    private val navHostFragment: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.main_nav_fragment) as NavHostFragment
    }

    override fun onSupportNavigateUp(): Boolean {
        currentFragment.onNavigationUp()
        return false
    }

    override fun createViewModel(): Class<MainViewModel> {
        return MainViewModel::class.java
    }

    override fun getContentView(): Int {
        return R.layout.activity_main
    }

    override fun initAction() {
    }

    override fun initData() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
        navController = navHostFragment.navController
        navController.addOnDestinationChangedListener(this)
        listenerAction()
        requestPermission()
        checkNetWork()
        listenerReport()
        mViewModel.getToken()
    }

//    private fun startSV(){
//        val intent = Intent(this,HomeStayService::class.java)
//        startService(intent)
//    }
    private fun listenerReport() {
        mViewModel.checkReport()
        mViewModel.listReportLiveData.observe(this, {
            printLog("listReportLiveData: ${it.size}")
            if (it.size >= 5 ) {
                startActivity(Intent(this, BlockActivity::class.java))
                mViewModel.logOut()
                finish()
            }else{
                return@observe
            }
        })
    }

    private fun checkNetWork() {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.let {
            it.registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {

                }

                override fun onLost(network: Network) {
                    Toast.makeText(this@MainActivity, "Mất kết nối !", Toast.LENGTH_LONG).show()
                }
            })

        }
    }

    private fun requestPermission() = if (
        ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.RECORD_AUDIO
        )
        != PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
        != PackageManager.PERMISSION_GRANTED
    ) {

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
            ), 1
        )
    } else {

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                requestPermission()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun listenerAction() {


        mViewModel.btnClick.observe(this, {
            when (it) {
                MainViewModel.BTN_HOME -> {
                    if (currentFragment is HomeFragment) {
                    } else {
                        navigate(R.id.home_fragment)
                    }

                }

                MainViewModel.BTN_MANAGER -> {
                    if (currentFragment is ManagerRoomFragment) {
                    } else {
                        navigate(R.id.manager_Room_fragment)
                    }
                }

                MainViewModel.BTN_ACCOUNT -> {
                    if (currentFragment is AccountFragment) {
                    } else {
                        navigate(R.id.account_fragment)
                    }
                }
            }
        })

    }


    override fun onBackPressed() {
        super.onBackPressed()
        if (currentFragment is HomeFragment) {
            finish()
        }
    }

    override fun navigateUp() {
        val isFinish = !navController.popBackStack()
        if (isFinish) {
            finish()
        }
    }

    override fun onFragmentResumed(fragment: BaseFragment<*, *>) {
        currentFragment = fragment
        printLog("currentFragment: ${fragment::class.simpleName}")
    }

    private fun getNavOptionsNavigate(): NavOptions.Builder? {
        return NavOptions.Builder()
            .setEnterAnim(R.anim.slide_in)
            .setExitAnim(R.anim.fade_out)
            .setPopEnterAnim(R.anim.fade_in)
            .setPopExitAnim(R.anim.slide_out)
    }

    override fun navigate(
        fragmentId: Int,
        bundle: Bundle?,
        addToBackStack: Boolean
    ) {
        val navOptions = getNavOptionsNavigate()
        if (!addToBackStack) {
            navOptions?.setPopUpTo(currentFragmentId, true)
        }
        navController.navigate(fragmentId, bundle, navOptions?.build())
        currentFragmentId = fragmentId
    }


    override fun navigateWithSharedElement(
        fragmentId: Int,
        bundle: Bundle?,
        sharedElements: FragmentNavigator.Extras?,
        addToBackStack: Boolean
    ) {

        val navOptions = getNavOptionsNavigate()
        if (!addToBackStack) {
            navOptions?.setPopUpTo(currentFragmentId, true)
        }
        navController.navigate(fragmentId, bundle, null, sharedElements)
    }

    override fun present(fragmentId: Int, bundle: Bundle?) {
        currentFragmentId = fragmentId
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        val currentFragment = navHostFragment.childFragmentManager.fragments.firstOrNull()


    }
}