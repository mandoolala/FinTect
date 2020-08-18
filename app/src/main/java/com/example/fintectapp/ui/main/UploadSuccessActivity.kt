package com.example.fintectapp.ui.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.fintectapp.MainActivity
import com.example.fintectapp.R
import com.example.fintectapp.ui.main.contents.AuthContent
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.android.synthetic.main.activity_upload_success.*

class UploadSuccessActivity : AppCompatActivity() {
    lateinit var documentId: String
    lateinit var path: String
    lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_success)

        button_to_home.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            finish()
        }

        documentId = intent.getStringExtra("documentId")
        path = intent.getStringExtra("path")
        db = FirebaseFirestore.getInstance()
    }

    override fun onStart() {
        super.onStart()

        db.collection("company").document(documentId)
            .set(
                hashMapOf(
                    "flag" to true,
                    "status" to "평가중..."
                ), SetOptions.merge()
            )
            .addOnSuccessListener { docRef ->
                Log.d("UPDATE DOC", "Finish updating document: ${documentId}")
            }
            .addOnFailureListener { e ->
                Log.w("UPDATE DOC", "Error updating document", e)
            }

        db.collection("verify-queue")
            .add(
                mapOf(
                    "is_processed" to false,
                    "video_url" to path,
//                    "created" to FieldValue.serverTimestamp(),
                    "company_id" to documentId as String
                )
            )
            .addOnSuccessListener { docRef ->
                Log.d("ADD DOC", "Add new document: ${docRef.id}")
            }
            .addOnFailureListener { e ->
                Log.w("ADD DOC", "Error adding document", e)
            }

    }
}
