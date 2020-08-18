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

    private var COUNT1 = 0
    private var COUNT2 = 0

    init {
        // query1 is for "평가중..." company

    }
    fun refreshHistDB(){
        ITEMS.clear()
        val query = db.collection("verify-queue")
        query.get().addOnSuccessListener { documents ->
            for (document in documents!!) {
                val companyType = document.data.getValue("is_processed")
                if(companyType == true){
                    val is_valid = document.data.getValue("is_valid")
                    val companyId = document.data.getValue("company_id")
                    val companyDocRef = db.collection("company").document(companyId as String)
                    companyDocRef.get()
                        .addOnSuccessListener { companyDoc ->
                            Log.e("DB2 DATA", "${companyDoc.data}")
                            val name: String = companyDoc?.data?.get("name") as String
                            val position: Int = (companyDoc?.data?.get("position") as Long)?.toInt()
                            val status = if (is_valid as Boolean) "승인 완료" else "승인 거절"
                            val flag: Boolean = companyDoc?.data?.get("flag") as Boolean
                            addItem(createDummyItem(name, position, status, flag))
                        }.addOnFailureListener { e ->
                            Log.d("COMPANY DOC", "get failed with ", e)
                        }
                    COUNT2 += 1
                }else if(companyType == false){
                    val companyId = document.data.getValue("company_id")
                    val companyDocRef = db.collection("company").document(companyId as String)
                    companyDocRef.get()
                        .addOnSuccessListener { companyDoc ->
                            Log.e("DB1 DATA", "${companyDoc.data}")
                            val name: String = companyDoc?.data?.get("name") as String
                            val position: Int = (companyDoc?.data?.get("position") as Long)?.toInt()
                            val status = "평가중..."
                            val flag: Boolean = companyDoc?.data?.get("flag") as Boolean
                            addItem(createDummyItem(name, position, status, flag))
                        }.addOnFailureListener { e ->
                            Log.d("COMPANY DOC", "get failed with ", e)
                        }
                    COUNT1 += 1
                }

            }
        }.addOnFailureListener { e ->
            Log.d("Q2", "get failed with ", e)
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