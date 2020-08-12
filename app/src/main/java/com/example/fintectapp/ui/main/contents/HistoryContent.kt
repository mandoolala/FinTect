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
object HistoryContent {

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
        val query = db.collection("company").whereEqualTo("flag", true)
        val registration = query.addSnapshotListener { documents, e ->
            if (e != null) {
                Log.w("DB", "Listen failed.", e)
                return@addSnapshotListener
            }

            for (document in documents!!) {
                Log.d("DATA", "${document?.data}")
                val name: String = document?.data?.get("name") as String
                val position: Int = (document?.data?.get("position") as Long)?.toInt()
                val status: String = document?.data?.get("status") as String
                val flag: Boolean = document?.data?.get("flag") as Boolean
                addItem(createDummyItem(name, position, status, flag))
                COUNT += 1
            }
        }

        if (COUNT > 0) {
            registration.remove()
        } else {
            registration
        }
    }

    private fun addItem(item: DummyItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createDummyItem(name: String, position: Int, status: String, flag: Boolean): DummyItem {
        return DummyItem(position.toString(), name, status, flag)
    }
    /**
     * A dummy item representing a piece of content.
     */
    data class DummyItem(val id: String, val content: String, val status: String, val flag: Boolean) {
        override fun toString(): String = content
    }
}