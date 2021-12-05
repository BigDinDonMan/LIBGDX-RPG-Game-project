package com.rpgproject.ui

import com.badlogic.gdx.scenes.scene2d.InputEvent
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener
import com.rpgproject.inventory.Inventory
import de.golfgl.gdx.controllers.IControllerActable

class InventorySlot(slotSkin: Skin?, val inventoryIndex: Int) : ImageButton(slotSkin), IControllerActable {

    init {
        addListener(object : ClickListener() {
            override fun clicked(event: InputEvent?, x: Float, y: Float) {
                println("${Inventory.itemAt(inventoryIndex)}, ${inventoryIndex}")
            }
        })
    }

    override fun onControllerDefaultKeyDown(): Boolean {
        TODO("Not yet implemented")
    }

    override fun onControllerDefaultKeyUp(): Boolean {
        TODO("Not yet implemented")
    }

}