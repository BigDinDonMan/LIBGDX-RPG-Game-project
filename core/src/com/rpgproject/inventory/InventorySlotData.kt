package com.rpgproject.inventory

data class InventorySlotData(var item: InventoryItem? = null, var amount: Int = 0) {


    fun clear() {
        item = null
        amount = 0
    }
}
