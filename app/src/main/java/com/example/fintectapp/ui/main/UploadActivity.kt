package com.example.fintectapp.ui.main

import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fintectapp.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_upload.*
import java.io.File


class UploadActivity : AppCompatActivity() {
    lateinit var storage: FirebaseStorage
    lateinit var name: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)

        storage = Firebase.storage

        if (intent.hasExtra("nameKey")) {
            name = intent.getStringExtra("nameKey")
            textView4.text = name
            /* "nameKey"라는 이름의 key에 저장된 값이 있다면
               textView의 내용을 "nameKey" key에서 꺼내온 값으로 바꾼다 */

        } else {
            Toast.makeText(this, "전달된 이름이 없습니다", Toast.LENGTH_SHORT).show()
        }

//      Button 추가하기
        val Uploadbutton: Button = findViewById<View>(R.id.VideoUploadbutton) as Button


        Uploadbutton.setOnClickListener {
            // TODO : click event
            val intent = Intent()
            intent.type = "video/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent,"Select Video"),1);
        }
    }

    fun getPath(uri: Uri?): String? {
        val projection =
            arrayOf(MediaStore.Video.Media.DATA)
        val cursor: Cursor = getContentResolver().query(uri!!, projection, null, null, null)!!
        return if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            val column_index: Int = cursor
                .getColumnIndexOrThrow(MediaStore.Video.Media.DATA)
            cursor.moveToFirst()
            cursor.getString(column_index)
        } else null
    }
    private fun getRealPathFromURI(contentUri: Uri): String? {
        if (contentUri.path!!.startsWith("/storage")) {
            return contentUri.path
        }
        val id =
            DocumentsContract.getDocumentId(contentUri).split(":".toRegex()).toTypedArray()[1]
        val columns =
            arrayOf(MediaStore.Files.FileColumns.DATA)
        val selection = MediaStore.Files.FileColumns._ID + " = " + id
        val cursor = contentResolver.query(
            MediaStore.Files.getContentUri("external"),
            columns,
            selection,
            null,
            null
        )
        try {
            val columnIndex = cursor!!.getColumnIndex(columns[0])
            if (cursor.moveToFirst()) {
                return cursor.getString(columnIndex)
            }
        } finally {
            cursor!!.close()
        }
        return null
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                val selectedImageUri: Uri? = data!!.data

                // OI FILE Manager
                val filemanagerstring = selectedImageUri!!.path

                // MEDIA GALLERY
                val selectedImagePath = getRealPathFromURI(selectedImageUri)
                var documentId: String = ""
                if (selectedImagePath != null) {
                    Log.e("PATH", selectedImagePath)
                    // PATH는 잘 저장되어 있음
                    // 여기서 FireBase로 전송하면 될 듯
                    // Toast.makeText(this, "비디오 업로드 성공!", Toast.LENGTH_SHORT).show()
                    val storageRef = storage.getReferenceFromUrl("gs://fintect-ff87d.appspot.com/")
                    val file = Uri.fromFile(File(selectedImagePath))
                    val videoRef = storageRef.child("videos/${name}.mp4")
                    val uploadTask = videoRef.putFile(file)

                    Log.d("UPLOADING VIDEO...", "${file}")
                    uploadTask.addOnFailureListener {
                        Log.e("UPLOAD VIDEO", "FAILED")
                    }.addOnSuccessListener {
                        Log.d("UPLOAD VIDEO", "SUCCESS")
                        val db = FirebaseFirestore.getInstance()
                        db.collection("company").whereEqualTo("name", name)
                            .get()
                            .addOnSuccessListener { documents ->
                                for (document in documents) {
                                    documentId = document.id
                                    break
                                }

                                val intent = Intent(
                                    this,
                                    UploadSuccessActivity::class.java
                                )
                                Log.e("DOC ID", "${documentId}")
                                intent.putExtra("path", "videos/${name}.mp4")
                                intent.putExtra("documentId", documentId)
                                startActivity(intent)
                            }.addOnFailureListener { e ->
                                Log.w("GET DOC", "Error getting document", e)
                            }
                    }
                }
            }
        }
    }
}


