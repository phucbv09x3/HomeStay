package com.kujira.homestay.ui.client.ar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.kujira.homestay.R
import com.google.ar.core.ArCoreApk
import com.google.ar.core.ArCoreApk.Availability


class ARActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aractivity)
    }
}