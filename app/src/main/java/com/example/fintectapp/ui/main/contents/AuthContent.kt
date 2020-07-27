package com.example.fintectapp.ui.main.contents

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

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, DummyItem> = HashMap()

    private val COUNT = 4

    init {
        // Add some sample items.
        addItem(createDummyItem("NH농협은행", 1, false))
        addItem(createDummyItem("KB국민은행",2, false))
        addItem(createDummyItem("IBK투자증권", 3, false))
        addItem(createDummyItem("신한카드", 4, false))
    }

    private fun addItem(item: DummyItem) {
        ITEMS.add(item)
        ITEM_MAP.put(item.id, item)
    }

    private fun createDummyItem(name: String, position: Int, flag: Boolean): DummyItem {
        return DummyItem(position.toString(), name, flag, makeDetails(position))
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

    /**
     * A dummy item representing a piece of content.
     */
    data class DummyItem(val id: String, val content: String, val flag: Boolean, val details: String) {
        override fun toString(): String = content
    }
}