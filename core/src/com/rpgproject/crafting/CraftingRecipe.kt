package com.rpgproject.crafting

import com.rpgproject.inventory.InventoryItem

class CraftingRecipe(val outputItem: InventoryItem) {
    private val materials: HashMap<InventoryItem, Int> = HashMap()
    private val items: HashMap<InventoryItem, Int> = HashMap()
}
