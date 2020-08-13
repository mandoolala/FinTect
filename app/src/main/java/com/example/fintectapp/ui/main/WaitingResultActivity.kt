package com.example.fintectapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fintectapp.R

class WaitingResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("RESULT ACTIVITY", "WAITING ACTIVITY")
        setContentView(R.layout.activity_waiting_status_result)
    }
}