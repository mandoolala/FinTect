package com.example.fintectapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register_agreement.*

class RegisterAgreementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_agreement)
        deny_button.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        agree_button.setOnClickListener { signupFinished() }
    }

    private fun signupFinished() {
//        TODO("MainActivity로 넘어가기 전에 로그인 시키고 바로 Main ㄱㄱ" )
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}