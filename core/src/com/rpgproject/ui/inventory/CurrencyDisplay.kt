package com.rpgproject.ui.inventory

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.scenes.scene2d.ui.Image
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.rpgproject.ui.AnimatedCountdownLabel

class CurrencyDisplay(currencyIcon: Texture) : Table() {
    private val currencyIconImage = Image(currencyIcon)
    //todo: add animated countdown label here
    private val currencyLabel = AnimatedCountdownLabel("", 0)

    init {
        row()
        add(currencyIconImage).padRight(5f)
        add(currencyLabel).padLeft(5f)
    }

    fun update(old: Int, current: Int) {
        currencyLabel.startCountdown(old, current)
    }
}
