package com.rpgproject.ui

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.rpgproject.inventory.Inventory
import com.rpgproject.inventory.InventoryItem

class InventorySlot(slotSkin: Skin?, val inventoryIndex: Int) : ImageButton(slotSkin) {//todo: add Image field for displaying item icon

    private val itemIcon = Image()

    init {
        addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                selectSlot()
            }
        })
    }

    private fun selectSlot() {
        println("${Inventory.itemAt(inventoryIndex)}, ${inventoryIndex}")
    }

    private fun updateItemIcon(item: InventoryItem) {
    }
}