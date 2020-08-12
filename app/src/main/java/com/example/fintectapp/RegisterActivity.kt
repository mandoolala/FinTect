package com.example.fintectapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

//    var auth : FirebaseAuth? = null
//    var db : FirebaseFirestore? = null
    private lateinit var auth: FirebaseAuth
    private fun createUserId(email: String, password: String){
        Log.e("LOGIN", "START")
//        showProgressBar()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.e("LOGIN", "success")
                    val intent = Intent(this, RegisterAgreementActivity::class.java)
                    startActivity(intent)
                } else {
                    Log.e( "LOGIN","createUserWithEmail:failure",task.getException())
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
//                hideProgressBar()
            }
        Log.e("LOGIN", "END")

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = Firebase.auth
        if(auth == null){
            Log.e("AUTH","IS NULL")
        }else{
            Log.e("AUTH", "IS NOT NULL")
        }
//        db = FirebaseFirestore.getInstance()
        register_button.setOnClickListener{ _ ->
            if (register_password_area.text.toString() == register_confirm_password_area.text.toString()){
//                createData(register_email_area.text.toString(), register_phoneNumber_area.text.toString())
                Log.e("LOG", "REGISTER START")
                createUserId(register_email_area.text.toString(), register_password_area.text.toString())
//                auth?.signOut()
                Log.e("LOG", "REGISTER END")

            }
            else {
                Toast.makeText(this, "confirm password", Toast.LENGTH_LONG).show()
            }
        }
    }
//
//    private fun createData(email: String, phone: String) {
//        val userDTO = UserDTO(email, phone, 0)
////        db = FirebaseFirestore.getInstance()
//        db?.collection("User")?.document(email)?.set(userDTO)?.addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                Log.d("REGISTER", "success")
//                Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
}