package com.rpgproject.ui

import com.badlogic.gdx.graphics.g2d.Batch
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton
import com.badlogic.gdx.scenes.scene2d.ui.Skin

class InventorySlot(slotSkin: Skin?) : Actor() {

    private val slotButton by lazy { ImageButton(slotSkin) }

    init {

    }

    override fun draw(batch: Batch, parentAlpha: Float) {
        super.draw(batch, parentAlpha)
        slotButton.draw(batch, parentAlpha)
    }

    override fun act(delta: Float) {
        super.act(delta)
        slotButton.act(delta)
    }
}