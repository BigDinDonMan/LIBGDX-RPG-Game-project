package com.rpgproject.inventory

import com.rpgproject.util.observer.TripleArgGameEvent

//maybe add onItemChanged callback?
object Inventory {

    private const val inventorySize = 24

    //todo: change Pair<InventoryItem, Int> to InventorySlotData and make it not nullable
    val items = Array<Pair<InventoryItem, Int>?>(inventorySize) { null }
    var currency = 0

    //<editor-fold desc="Inventory callbacks">

    val onArtifactEquipped: Any? = null //this is going to be a callback once implemented
    val onItemAdded = TripleArgGameEvent<InventoryItem, Int, Int>() //item, position in inventory, current count
    val onItemRemoved = TripleArgGameEvent<InventoryItem, Int, Int>() //ditto

    //</editor-fold>

    //<editor-fold desc="Item add & remove logic">
    fun addItem(item: InventoryItem, count: Int): Int {
        var currentCount = count
        for (index in items.indices) {
            if (currentCount <= 0) {
                break
            }
            val current = items[index]
            if (current != null && current.first == item) {
                val stackCount = current.second
                var newSize = count + stackCount
                val remainder = newSize % current.first.maxStack
                newSize -= remainder
                items[index] = Pair(current.first, newSize)
                onItemAdded.invoke(item, index, newSize)
                currentCount = remainder
            } else if (current == null) {
                items[index] = Pair(item, count)
                onItemAdded.invoke(item, index, count)
                currentCount = 0
            } else continue
        }

        return currentCount
    }

    fun removeItem(item: InventoryItem, count: Int): Boolean {
        val slotsWithItem = ArrayList<Pair<Int, Int>>() //list of pairs index: count
        var currentCount = count
        for (index in items.indices) {
            if (currentCount <= 0) break
            val current = items[index]
            if (current != null && current.first == item) {
                var subtractedFromSlot = 0
                if (currentCount >= current.second) {
                    currentCount -= current.second
                    subtractedFromSlot = current.second
                } else {
                    currentCount = 0
                    subtractedFromSlot = current.second - currentCount
                }
                slotsWithItem += Pair(index, subtractedFromSlot)
            }
        }

        if (currentCount > 0) {
            return false
        }

        slotsWithItem.forEach { (slotIndex, subtractedCount) -> kotlin.run {
            val currentItem = items[slotIndex]!!
            val currentItemCount = currentItem.second - subtractedCount
            items[slotIndex] = if (currentItemCount <= 0) null else Pair(currentItem.first, currentItemCount)
            onItemRemoved.invoke(currentItem.first, slotIndex, currentItemCount)
        } }

        return true
    }

    fun itemAt(position: Int) = items[position]
    //</editor-fold>
}
