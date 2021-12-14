package com.rpgproject.ui

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.rpgproject.inventory.Inventory

class InventorySlot(slotSkin: Skin?, val inventoryIndex: Int) : ImageButton(slotSkin) {//todo: add Image field for displaying item icon

    private val itemIcon = Image()
    private val itemAmountLabel = Label("TEMP", slotSkin)

    init {
        addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                selectSlot()
            }
        })
        addActor(itemIcon)
        addActor(itemAmountLabel)
    }

    private fun selectSlot() {
        println("${Inventory.itemAt(inventoryIndex)}, ${inventoryIndex}")
    }

    fun updateDisplay() {
        val itemData = Inventory.itemAt(inventoryIndex)
        itemIcon.drawable = if (itemData == null) null else TextureRegionDrawable(itemData.first.icon)
        //todo: only set the text to count if count is more than 1
        itemAmountLabel.setText(itemData?.second?.toString() ?: "")
    }
}
