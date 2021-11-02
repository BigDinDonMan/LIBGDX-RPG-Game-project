package com.rpgproject.ui

import com.badlogic.gdx.scenes.scene2d.ui.Skin
import com.badlogic.gdx.scenes.scene2d.ui.Window
import com.rpgproject.inventory.Inventory

//this class will subscribe to Inventory.itemAdded and Inventory.itemRemoved component to properly update the ui
class InventoryWindow : Window {

    constructor(title: String, style: WindowStyle) : super(title, style) {
        initUI()
    }

    constructor(title: String, skin: Skin) : super(title, skin) {
        initUI()
    }

    private fun initUI() {
        val slotsPerRow = 6 //might be subject to change
        val rows = Inventory.items.size / slotsPerRow
        for (i in 0 until rows) {
            row()
            for (j in 0 until slotsPerRow) {
                addActor(InventorySlot())
            }
        }
    }

    private fun setUpInventoryListeners() {

    }
}