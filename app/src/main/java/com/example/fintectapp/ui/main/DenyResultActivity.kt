package com.example.fintectapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fintectapp.R

class DenyResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("RESULT ACTIVITY", "DENY ACTIVITY")
        setContentView(R.layout.activity_deny_result)
    }
}