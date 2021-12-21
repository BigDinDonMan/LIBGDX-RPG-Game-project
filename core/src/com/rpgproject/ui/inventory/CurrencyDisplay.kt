package com.rpgproject.ui.inventory

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Table

class CurrencyDisplay(currencyIcon: Texture, labelSkin: Skin) : Table() {
    private val currencyIconImage = Image(currencyIcon)
    private val currencyLabel = Label("", labelSkin)

    init {
        row()
        add(currencyIconImage).padRight(5f)
        add(currencyLabel).padLeft(5f)
    }

    fun update(currency: Int) {
        currencyLabel.setText(currency)
    }
}
