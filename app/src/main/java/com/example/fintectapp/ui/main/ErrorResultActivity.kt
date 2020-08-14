package com.example.fintectapp.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fintectapp.R

class ErrorResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("RESULT ACTIVITY", "ERROR ACTIVITY")
        setContentView(R.layout.activity_error_result)
    }
}