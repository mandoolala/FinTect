package com.example.fintectapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login_bottom.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        setContentView(R.layout.activity_login)
        google_login_button.setOnClickListener { googleLogin() }
        login_button.setOnClickListener { emailLogin() }
        register_page.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }


    }

    private fun emailLogin() {
        Log.e("emailLogin","HERE")
        if (email_area.text.toString().isEmpty() || password_area.text.toString().isEmpty()) {
            Toast.makeText(this, "Null email or password", Toast.LENGTH_SHORT).show()
        }
        else {
            signinEmail()
        }

    }

    private fun signinEmail() {
        auth?.signInWithEmailAndPassword(email_area.text.toString(), password_area.text.toString())
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                else{
                    Toast.makeText(this, "Invalid ID or password", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun googleLogin() {
//        TODO("Not yet implemented")
        Log.e("googleLogin","HERE")
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onStart() {
        super.onStart()
        moveMainPage(auth?.currentUser)
    }

    fun moveMainPage(user: FirebaseUser?) {
        if (user != null){
            Log.e("moveMainPage","HERE")
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
//        }
    }

}