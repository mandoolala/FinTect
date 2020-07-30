package com.example.fintectapp.ui.main

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.fintectapp.R


class UploadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                val selectedImageUri: Uri? = data!!.data

                // OI FILE Manager
                val filemanagerstring = selectedImageUri!!.path

                // MEDIA GALLERY
                val selectedImagePath = getPath(selectedImageUri)
                if (selectedImagePath != null) {
                    val intent = Intent(
                        this,
                        UploadActivity::class.java
                    )
                    intent.putExtra("path", selectedImagePath)
                    Log.e("PATH",selectedImagePath)
                    // PATH는 잘 저장되어 있음
                    // 여기서 FireBase로 전송하면 될 듯
                    Toast.makeText(this, "비디오 업로드 성공!", Toast.LENGTH_SHORT).show()
//                    startActivity(intent)
                }
            }
        }
    }
}


