package com.example.fintectapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login_bottom.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

private const val RC_SIGN_IN = 9001
class LoginActivity : AppCompatActivity() {
    var auth : FirebaseAuth? = null
    private lateinit var googleSignInClient: GoogleSignInClient
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
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
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
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("D","signInWithCredential:success")
                    val user = auth?.currentUser

                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w( "signInWithCredential:failure", task.exception)
                    // ...
    //                    val vi
    //                    Snackbar.make(view, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
    //                    updateUI(null)
                }

                // ...
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("D", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w( "Google sign in failed", e)
                // ...
            }
        }
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