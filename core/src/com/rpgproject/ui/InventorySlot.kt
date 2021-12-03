package com.rpgproject.ui

import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Skin

class InventorySlot(slotSkin: Skin?) : Actor() {

    private val slotButton by lazy { ImageButton(slotSkin) }

    init {
        //add an image (inventory slot icon) and an actual item image
    }
}