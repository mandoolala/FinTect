package com.example.fintectapp.ui.main.contents

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

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, DummyItem> = HashMap()

    private val COUNT = 2

    init {
        // Add some sample items.
        addItem(createDummyItem("우리은행", 1, "승인 완료", true))
        addItem(createDummyItem("신한은행", 2, "승인 거절", true))

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