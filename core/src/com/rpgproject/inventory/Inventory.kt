package com.rpgproject.inventory

import com.artemis.Entity

object Inventory {
    val items = Array<InventoryItem?>(24) { null }
    var currency = 0

    //<editor-fold desc="Inventory callbacks">

    val onArtifactEquipped: Any? = null //this is going to be a callback once implemented
    val onItemAdded: ((Entity) -> Unit)? = null

    //</editor-fold>

    //<editor-fold desc="Item add & remove logic">
    //todo: invoke onItemAdded here
    fun addItem(item: InventoryItem) = itemAssignInternal(null, item)

    //todo: add onItemRemoved and invoke it here
    fun removeItem(item: InventoryItem) = itemAssignInternal(item, null)

    fun itemAt(position: Int) = items[position]

    private fun itemAssignInternal(check: InventoryItem?, assignment: InventoryItem?): Boolean {
        for (i in 0..items.size) {
            if (items[i] == check) {
                items[i] = assignment
                return true
            }
        }
        return false
    }
    //</editor-fold>
}