package com.rpgproject.ui.inventory

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.rpgproject.inventory.Inventory

class InventorySlot(slotSkin: Skin?, val inventoryIndex: Int) : ImageButton(slotSkin) {

    private val itemIcon = Image()
    private val itemAmountLabel = Label("", slotSkin)

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
        //todo: this function should be used for using the item (send use event)
    }

    fun updateDisplay() {
        val itemData = Inventory.itemAt(inventoryIndex)
        itemIcon.drawable = if (itemData.item == null) null else TextureRegionDrawable(itemData.item!!.icon)
        itemAmountLabel.setText(if (itemData.item == null) "" else if (itemData.amount > 1) itemData.amount.toString() else "")
    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        itemIcon.drawable?.draw(batch, x, y, width, height)
    }
}
