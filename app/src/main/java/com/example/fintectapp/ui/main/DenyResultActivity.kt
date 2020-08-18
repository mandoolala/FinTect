package com.example.fintectapp.ui.main

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.MediaController
import com.example.fintectapp.MainActivity
import com.example.fintectapp.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_deny_result.*

class DenyResultActivity : AppCompatActivity() {
    lateinit var name: String
    lateinit var db: FirebaseFirestore
    lateinit var storage: FirebaseStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deny_result)

        Log.d("RESULT ACTIVITY", "DENY ACTIVITY")

        db = FirebaseFirestore.getInstance()
        storage = Firebase.storage
        name = intent.getStringExtra("name")
        val statusVal = intent.getStringExtra("status")

        companyName.text = name
        status.text = statusVal


        homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

    }

    override fun onStart() {
        super.onStart()

        db.collection("verify-queue")
            .whereEqualTo("video_url", "videos/${name}.mp4")
            .get()
            .addOnSuccessListener { documents ->
                Log.w("GET DOC", "SUCCESS")
                for (document in documents) {
                    val percent = document.data.getValue("percentage")
                    resultDetailText.text = "영상 조작 가능성: ${percent}%"

                    val storageRef = storage.reference
                    val videoRef = storageRef.child("videos/${name}.mp4")
                    videoRef.downloadUrl.addOnSuccessListener { uri ->
                        videoView.setOnPreparedListener {mediaPlayer ->
                            mediaPlayer.isLooping = true
                        }

                        videoView.setVideoURI(uri)
                        videoView.requestFocus()
                        videoView.start()
                    }
                    break
                }
            }
            .addOnFailureListener { e ->
                Log.w("DB GET", "Getting percentage failed", e)
            }
    }
}