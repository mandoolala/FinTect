package com.example.fintectapp.ui.main.contents

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import java.util.ArrayList
import java.util.HashMap

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object AuthContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<DummyItem> = ArrayList()
    val db = FirebaseFirestore.getInstance()
    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, DummyItem> = HashMap()

    private var COUNT = 0

    init {
        // Add items(collection : "company") from firestore where flag is true

//        if (COUNT > 0) {
//            registration.remove()
//        } else {
//            registration
//        }
    }
    fun refreshAuthDB(){
        ITEMS.clear()
        val query = db.collection("company").whereEqualTo("flag", false)
//        val registration = query.get().addOnSuccessListener { documents, }
        query.get().addOnSuccessListener { documents ->

            for (document in documents!!) {
                Log.e("AUTH", "${document?.data}")
                val name: String = document?.data?.get("name") as String
                val position: Int = (document?.data?.get("position") as Long)?.toInt()
                val date: String = document?.data?.get("date") as String
                val flag: Boolean = document?.data?.get("flag") as Boolean
                addItem(createDummyItem(name, position, date, flag))
                COUNT += 1
            }
        }.addOnFailureListener { e ->
            Log.d("Q2", "get failed with ", e)
        }
    }
    private fun addItem(item: DummyItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createDummyItem(name: String, position: Int, date: String, flag: Boolean): DummyItem {
        return DummyItem(position.toString(), name,  makeDate(date), flag)
    }

    private fun makeDate(date: String): String {
        val builder = StringBuilder()
        builder.append("요청 일자: ").append(date)
        return builder.toString()
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class DummyItem(val id: String, val content: String, val date: String, val flag: Boolean) {
        override fun toString(): String = content
    }
}