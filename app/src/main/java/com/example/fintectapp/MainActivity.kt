package com.example.fintectapp

import android.content.res.ColorStateList
import android.graphics.Color.rgb
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.fintectapp.ui.main.SectionsPagerAdapter
import com.example.fintectapp.ui.main.contents.AuthContent
import com.example.fintectapp.ui.main.contents.HistoryContent
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        HistoryContent.refreshHistDB()

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
        AuthContent.refreshAuthDB()
        Thread.sleep(2000)
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setImageResource(R.drawable.ic_action_name)
        fab.backgroundTintList = ColorStateList.valueOf((rgb(240,128,128)))
        fab.setOnClickListener { view ->
            Firebase.auth.signOut()
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
            Toast.makeText(this, "LOGOUT Success", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("finish", true)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // To clean up all activities
            startActivity(intent)
            finish()
        }
    }
}
