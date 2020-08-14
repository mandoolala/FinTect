package com.example.fintectapp.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fintectapp.MainActivity
import com.example.fintectapp.R
import kotlinx.android.synthetic.main.activity_upload_agreement.*

class UploadAgreementActivity : AppCompatActivity() {
    lateinit var name: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_agreement)

        name = intent.getStringExtra("nameKey")
        companyName.text = name

        deny_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        agree_button.setOnClickListener {
            val intent = Intent(this, UploadActivity::class.java)
            intent.putExtra("nameKey", name)
            startActivity(intent)
            finish()
        }
    }
}