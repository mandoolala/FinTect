package com.example.fintectapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        register_button.setOnClickListener{ _ ->
            if (register_password_area.text.toString() == register_confirm_password_area.text.toString()){
                createData(register_email_area.text.toString(), register_phoneNumber_area.text.toString())
                createUserId(register_email_area.text.toString(), register_password_area.text.toString())
//                auth?.signOut()
                val intent = Intent(this, RegisterAgreementActivity::class.java)
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "confirm password", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun createData(email: String, phone: String) {
//        TODO("Not yet implemented")

    }

    private fun createUserId(email: String, phone: String) {
//        TODO("Not yet implemented")
    }
}