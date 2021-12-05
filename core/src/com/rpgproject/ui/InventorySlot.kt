package com.rpgproject.ui

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.rpgproject.inventory.Inventory

class InventorySlot(slotSkin: Skin?, val inventoryIndex: Int) : ImageButton(slotSkin) {

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
}