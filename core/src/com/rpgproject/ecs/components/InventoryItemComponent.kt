package com.rpgproject.ecs.components

import com.artemis.Component
import com.rpgproject.inventory.InventoryItem

class InventoryItemComponent() : Component() {
    var item: InventoryItem? = null
    var amount: Int = 0
}
