package com.rpgproject.inventory

import com.rpgproject.util.observer.DoubleArgGameEvent

//maybe add onItemChanged callback?
object Inventory {

    private const val inventorySize = 24

    val items = Array<InventoryItem?>(inventorySize) { null }
    //todo: make animated currency display (incrementing upwards/downwards, e.g. like in stardew valley)
    var currency = 0

    //<editor-fold desc="Inventory callbacks">

    val onArtifactEquipped: Any? = null //this is going to be a callback once implemented
    val onItemAdded = DoubleArgGameEvent<InventoryItem, Int>() //item and its position in inventory
    val onItemRemoved = DoubleArgGameEvent<InventoryItem, Int>() //ditto

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