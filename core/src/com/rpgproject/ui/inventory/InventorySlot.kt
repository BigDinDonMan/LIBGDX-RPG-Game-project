package com.rpgproject.ui.inventory

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable
import com.badlogic.gdx.utils.Align
import com.rpgproject.inventory.Inventory
import com.rpgproject.inventory.InventoryItem
import com.rpgproject.util.observer.GameEvent

class InventorySlot(slotSkin: Skin?, val inventoryIndex: Int) : ImageButton(slotSkin) {

    val onSlotClicked = GameEvent<InventoryItem?>()

    private val itemIcon = Image()
    //@note: TEMP is needed because without it, the label isnt drawn properly (it sticks out at the bottom)
    //this is probably due to an empty string not having width and height.
    private val itemAmountLabel = Label("TEMP", slotSkin)

    init {
        //todo: create onSlotClicked event and call it inside this clickListener (or think about some other solution)
        addListener(object : ClickListener() {
            override fun clicked(event: InputEvent, x: Float, y: Float) {
                onSlotClicked.invoke(Inventory.itemAt(inventoryIndex).item)
            }
        })
        addActor(itemIcon)
        addActor(itemAmountLabel)
        align(Align.bottomLeft)
        itemAmountLabel.setText("")
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

    fun isEmpty() = Inventory.itemAt(inventoryIndex).item == null
}
