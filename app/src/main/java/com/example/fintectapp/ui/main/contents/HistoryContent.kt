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
        val query1 = db.collection("verify-queue").whereEqualTo("is_processed", false)
        val registration1 = query1.addSnapshotListener { documents, e ->
            if (e != null) {
                Log.w("DB1", "Listen failed.", e)
                return@addSnapshotListener
            }

            for (document in documents!!) {
                val companyId: String = document.data.getValue("company_id") as String
                val companyDocRef = db.collection("company").document(companyId)
                companyDocRef.get()
                    .addOnSuccessListener { companyDoc ->
                        Log.w("DB1 DATA", "${companyDoc.data}")
                        val name: String = companyDoc?.data?.get("name") as String
                        val position: Int = (companyDoc?.data?.get("position") as Long)?.toInt()
                        val status = "평가중..."
                        val flag: Boolean = companyDoc?.data?.get("flag") as Boolean
                        addItem(createDummyItem(name, position, status, flag))
                    }
                    .addOnFailureListener { e ->
                        Log.d("COMPANY DOC", "get failed with ", e)
                    }
                COUNT1 += 1
            }
        }

        // query2 is for "승인 완료" or "승인 거절" company
        val query2 = db.collection("verify-queue").whereEqualTo("is_processed", true)
        val registration2 = query2.addSnapshotListener { documents, e ->
            if (e != null) {
                Log.w("DB1", "Listen failed.", e)
                return@addSnapshotListener
            }

            for (document in documents!!) {
                val companyId: String = document.data.getValue("company_id") as String
                val is_valid: Boolean = document.data.getValue("is_valid") as Boolean
                val companyDocRef = db.collection("company").document(companyId)
                companyDocRef.get()
                    .addOnSuccessListener { companyDoc ->
                        Log.w("DB2 DATA", "${companyDoc.data}")
                        val name: String = companyDoc?.data?.get("name") as String
                        val position: Int = (companyDoc?.data?.get("position") as Long)?.toInt()
                        val status = if (is_valid) "승인 완료" else "승인 거절"
                        val flag: Boolean = companyDoc?.data?.get("flag") as Boolean
                        addItem(createDummyItem(name, position, status, flag))
                    }
                    .addOnFailureListener { e ->
                        Log.d("COMPANY DOC", "get failed with ", e)
                    }
                COUNT2 += 1
            }
        }
        if (COUNT1 > 0) {
            registration1.remove()
        } else {
            registration1
        }
        if (COUNT2 > 0) {
            registration2.remove()
        } else {
            registration2
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