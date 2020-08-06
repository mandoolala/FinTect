package com.example.fintectapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.fintectapp.MainActivity
import com.example.fintectapp.R
import com.example.fintectapp.ui.main.contents.HistoryContent
import kotlinx.android.synthetic.main.activity_upload_success.*

class UploadSuccessActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_success)

        button_to_home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        button_to_main.setOnClickListener {
            TODO("해당 화면 보여주기")
        }

    }
}