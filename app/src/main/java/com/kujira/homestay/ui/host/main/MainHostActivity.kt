package com.kujira.homestay.ui.host.main

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.messaging.FirebaseMessaging
import com.kujira.homestay.R
import com.kujira.homestay.databinding.ActivityHostMainBinding
import com.kujira.homestay.ui.base.BaseActivity
import com.kujira.homestay.ui.base.BaseFragment
import com.kujira.homestay.ui.client.BlockActivity
import com.kujira.homestay.ui.host.add.AddRoomFragment
import com.kujira.homestay.ui.host.manager.ManagerRoomHostFragment
import com.kujira.homestay.ui.host.myacc.MyAccountHostFragment
import com.kujira.homestay.utils.printLog
import kotlinx.android.synthetic.main.activity_main.*


const val TOPIC = "/topics/myTopic2"

open class MainHostActivity : BaseActivity<MainHostViewModel, ActivityHostMainBinding>(),
    NavController.OnDestinationChangedListener {
    private lateinit var navController: NavController

    private lateinit var currentFragment: BaseFragment<*, *>
    private var currentFragmentId: Int = R.id.manager_Room_fragment
    private val navHostFragment: NavHostFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.main_host_nav_fragment) as NavHostFragment
    }

    override fun onSupportNavigateUp(): Boolean {
        currentFragment.onNavigationUp()
        return false
    }

    override fun createViewModel(): Class<MainHostViewModel> {
        return MainHostViewModel::class.java
    }

    override fun getContentView(): Int {
        return com.kujira.homestay.R.layout.activity_host_main
    }

    override fun initAction() {
    }


    override fun initData() {
        navController = navHostFragment.navController
        //setSupportActionBar(toolbar)
        window.statusBarColor = ContextCompat.getColor(this, com.kujira.homestay.R.color.white)
        navController.addOnDestinationChangedListener(this)
//        NavigationUI.setupActionBarWithNavController(this, navController)
        listenerAction()
        requestPermissionCamera()
        listenerReport()
        mViewModel.getTokenHost()
        FirebaseMessaging.getInstance().subscribeToTopic(com.kujira.homestay.ui.client.main.TOPIC)
        //setupFCM()
    }

//    private fun setupFCM() {
//        FirebaseService.sharedPref = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
////        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
////            FirebaseService.token = it.token
////            etToken.setText(it.token)
////        }
//        FirebaseMessaging.getInstance().token
//            .addOnCompleteListener(OnCompleteListener { task ->
//                if (!task.isSuccessful) {
//                    return@OnCompleteListener
//                }
//                val token: String? = task.result
//                FirebaseDatabase.getInstance().getReference("Client")
//                    .child("Account").child()
//
//            })
//        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)
//    }

    private fun listenerReport() {
        mViewModel.checkReport()
        mViewModel.dataReport.observe(this, {
            printLog("adminBlock: $it")
            if (it == "AdminBlock") {
                startActivity(Intent(this, BlockActivity::class.java))
                mViewModel.logOut()
                finish()
            } else {
                return@observe
            }
        })
    }

    private fun requestPermissionCamera() = if (
        ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.CAMERA
        ) != PackageManager.PERMISSION_GRANTED
        && ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.RECORD_AUDIO
        )
        != PackageManager.PERMISSION_GRANTED
    ) {

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.RECORD_AUDIO,
            ), 1
        )
    } else {
        // startLocalStream()
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //startLocalStream()
            } else {
                requestPermissionCamera()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun listenerAction() {
        mViewModel.onClickMain.observe(this, {
            when (it) {
//                MainViewModel.BTN_MESSAGE -> {
//                    if (currentFragment is MessageFragment) {
//
//                    } else {
//                        navigate(R.id.messageFragment)
//                    }
//
//                }
                MainHostViewModel.BTN_MANAGER_ROOM -> {
                    if (currentFragment is ManagerRoomHostFragment) {

                    } else {
                        navigate(R.id.managerRoomHost)
                    }

                }

                MainHostViewModel.BTN_ADD_ROOM -> {
                    if (currentFragment is AddRoomFragment) {

                    } else {
                        navigate(R.id.addRoomHost)
                    }

                }
                MainHostViewModel.BTN_MY_ACC -> {
                    if (currentFragment is MyAccountHostFragment) {

                    } else {
                        navigate(R.id.myAccHost)
                    }

                }

            }
        })
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