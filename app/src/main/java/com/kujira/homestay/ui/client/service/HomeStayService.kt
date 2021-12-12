package com.kujira.homestay.ui.client.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Build
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.kujira.homestay.R
import com.kujira.homestay.ui.client.main.MainActivity
import kotlinx.coroutines.cancel


/**
 * Created by Phuc on 20/10/2021
 */
class HomeStayService : Service() {
    private lateinit var notificationManager: NotificationManager
    private lateinit var locationManager: LocationManager
    private lateinit var notificationLayoutCustom: RemoteViews
    private lateinit var notification: NotificationCompat.Builder
    private lateinit var pendingIntent: PendingIntent


    companion object {
        const val CHANNEL_ID = "IDMapService"
        const val CHANNEL_NAME = "MapService"
        const val NOTIFICATION_ID = 100
        const val NOTIFICATION_TITLE = "HomeStay VN"
        const val ACTION_CLOSE = "CLOSE"
        const val DESCRIPTION = "YOUR_NOTIFICATION_CHANNEL_DESCRIPTION"
        const val TEXT_NOTIFY_LOADING = "HomeStay running"
        const val REQUEST_CODE = 1
    }


    override fun onCreate() {
        super.onCreate()

        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationLayoutCustom = RemoteViews(packageName, R.layout.custom_notification)
        notification = NotificationCompat.Builder(this, CHANNEL_ID)
        registerNotificationChannel()
        createNotification()

    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_CLOSE -> {
                stopSelf()
                notificationManager.cancel(NOTIFICATION_ID)
            }
        }
        return START_NOT_STICKY
    }

    private fun registerNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = DESCRIPTION
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun createNotification() {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, REQUEST_CODE, intent, 0)

        val intentCoinService = Intent(this, HomeStayService::class.java).setAction(ACTION_CLOSE)
        val pendingIntentService =
            PendingIntent.getService(this, REQUEST_CODE, intentCoinService, 0)
        notificationLayoutCustom.setImageViewResource(
            R.id.img_notification,
            R.drawable.homstay_new
        )
        notificationLayoutCustom.setTextViewText(R.id.tv_title, NOTIFICATION_TITLE)
        notificationLayoutCustom.setTextViewText(R.id.tv_notification_user, TEXT_NOTIFY_LOADING)
        notificationLayoutCustom.setOnClickPendingIntent(R.id.btn_closeNoti, pendingIntentService)

        notification.setContentIntent(pendingIntent)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setCustomContentView(notificationLayoutCustom)
            .setSmallIcon(R.drawable.homstay_new)
            .setPriority(NotificationCompat.PRIORITY_LOW)

        startForeground(NOTIFICATION_ID, notification.build())

    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}