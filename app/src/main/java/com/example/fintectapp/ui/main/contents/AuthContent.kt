package com.example.fintectapp.ui.main.contents

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.util.*
import kotlinx.coroutines.*
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
    var FINISHED = false
    init {
//        Log.e("DATA","LODAED 1 ")
//
//        // Add items(collection : "company") from firestore where flag is true
//        val query = db.collection("company").whereEqualTo("flag", false)
//        Log.e("DATA","LODAED 2 ")
//
//        val task: Task<QuerySnapshot> = query.get()
//        Log.e("DATA","LODAED 3 ")
////        val snap: QuerySnapshot? = Tasks.await(task)
//        Log.e("DATA","LODAED 4 ")
//
//        val registration = query.addSnapshotListener { documents, e ->
//            if (e != null) {
//                Log.w("DB", "Listen failed.", e)
//                return@addSnapshotListener
//            }
//            Log.e("DATA","LODAED 5 ")
//
//            for (document in documents!!) {
//                Log.e("DATA", "${document?.data}")
//                val name: String = document?.data?.get("name") as String
//                val position: Int = (document?.data?.get("position") as Long)?.toInt()
//                val date: String = document?.data?.get("date") as String
//                val flag: Boolean = document?.data?.get("flag") as Boolean
////                addItem(createDummyItem(name, position, date, flag))
////                COUNT += 1
//            }
//            FINISHED = true
//        }
//
//        if (COUNT > 0) {
//            registration.remove()
//        } else {
//            registration
//        }

    }


    fun refreshDB() {
        Log.e("AUTH","LODAED 1 ")

        // Add items(collection : "company") from firestore where flag is true
        val query = db.collection("company").whereEqualTo("flag", false)
        Log.e("AUTH","LODAED 2 ")

        runBlocking<Unit> {
            launch {

                delay(1000L)
                Log.e("TAG", "World")

            }
            Log.e("TAG", "Hello")
            val task: Task<QuerySnapshot> = query.get()
            Log.e("AUTH","LODAED 3 ")
//            val snap: QuerySnapshot? = Tasks.await(task)

            val registration = query.addSnapshotListener { documents, e ->
                if (e != null) {
                    Log.w("DB", "Listen failed.", e)
                    return@addSnapshotListener
                }
                Log.e("AUTH","LODAED 4 ")

                for (document in documents!!) {
                    Log.e("AUTH", "${document?.data}")
                    val name: String = document?.data?.get("name") as String
                    val position: Int = (document?.data?.get("position") as Long)?.toInt()
                    val date: String = document?.data?.get("date") as String
                    val flag: Boolean = document?.data?.get("flag") as Boolean
                    addItem(createDummyItem(name, position, date, flag))
                    COUNT += 1
                }
                FINISHED = true
            }

            if (COUNT > 0) {
                registration.remove()
            } else {
                registration
            }
            delay(1000L)
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