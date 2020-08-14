package com.example.fintectapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fintectapp.MainActivity
import com.example.fintectapp.R
import kotlinx.android.synthetic.main.activity_deny_result.*

class ApproveResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approve_result)
        Log.d("RESULT ACTIVITY", "APPROVE ACTIVITY")

        val nameVal = intent.getStringExtra("name")
        val statusVal = intent.getStringExtra("status")

        companyName.text = nameVal
        status.text = statusVal

        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }


    }
}