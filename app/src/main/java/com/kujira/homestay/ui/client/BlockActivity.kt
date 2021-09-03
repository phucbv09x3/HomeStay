package com.kujira.homestay.ui.client

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.kujira.homestay.R

class BlockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block)
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
    }
}